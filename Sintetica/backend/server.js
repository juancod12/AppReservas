const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const port = 3002;

app.use(bodyParser.json());
app.use(cors());

// Conexión a MySQL
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '12152005David@',
    database: 'negocio'
});

connection.connect((err) => {
    if (err) {
        console.error('❌ Error conectando a la base de datos:', err);
        return;
    }
    console.log('✅ Conectado a la base de datos MySQL');
});

// Verificar disponibilidad
app.post('/verificar-disponibilidad', async (req, res) => {
    const { fecha, hora, cancha: tipo } = req.body;

    try {
        const [canchas] = await connection.promise().query(
            'SELECT id FROM canchas WHERE tipo = ? ORDER BY id ASC',
            [tipo]
        );

        for (const cancha of canchas) {
            const [result] = await connection.promise().query(
                `SELECT * FROM reservas
                WHERE id_cancha = ? AND fecha = ? 
                AND TIME(?) BETWEEN hora AND ADDTIME(hora, '01:00:00')`,
                [cancha.id, fecha, hora]
            );

            if (result.length === 0) {
                return res.json({ disponible: true }); // Al menos una libre
            }
        }

        res.json({ disponible: false });

    } catch (err) {
        console.error('❌ Error al verificar disponibilidad:', err);
        res.status(500).json({ error: 'Error al verificar disponibilidad' });
    }
});

// Guardar reserva con asignación automática de cancha
app.post('/reservar', async (req, res) => {
    const { nombre, telefono, correo, fecha, hora, cancha: tipo } = req.body;

    try {
        const [canchas] = await connection.promise().query(
            'SELECT id FROM canchas WHERE tipo = ? ORDER BY id ASC',
            [tipo]
        );

        let canchaLibre = null;

        for (const cancha of canchas) {
            const [result] = await connection.promise().query(
                `SELECT * FROM reservas
                WHERE id_cancha = ? AND fecha = ? 
                AND TIME(?) BETWEEN hora AND ADDTIME(hora, '01:00:00')`,
                [cancha.id, fecha, hora]
            );

            if (result.length === 0) {
                canchaLibre = cancha.id;
                break;
            }
        }

        if (!canchaLibre) {
            return res.status(400).send('❌ No hay canchas disponibles para ese horario.');
        }

        await connection.promise().query(
            `INSERT INTO reservas (nombre, fecha, hora, id_cancha, telefono, correo)
            VALUES (?, ?, ?, ?, ?, ?)`,
            [nombre, fecha, hora, canchaLibre, telefono, correo]
        );

        res.send('✅ Reserva guardada correctamente');

    } catch (err) {
        console.error('❌ Error al guardar la reserva:', err);
        res.status(500).send('Error al guardar la reserva');
    }
});

// Iniciar el servidor
app.listen(port, () => {
    console.log(`🚀 Servidor backend corriendo en http://localhost:${port}`);
});
