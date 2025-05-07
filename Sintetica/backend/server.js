const express = require('express');
const mysql = require('mysql2'); 
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const port = 3001; // Cambia el puerto


// Middleware
app.use(bodyParser.json());
app.use(cors());

// Configuración de la conexión a MySQL
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root', 
    password: '12152005David@', //Cambiar a su contraseña de Mysql
    database: 'negocio' 
});

// Conectar a MySQL
connection.connect((err) => {
    if (err) {
        console.error('Error conectando a la base de datos:', err);
        return;
    }
    console.log('Conectado a la base de datos MySQL');
});

// Ruta para verificar disponibilidad
app.post('/verificar-disponibilidad', (req, res) => {
    const { fecha, hora, cancha } = req.body;

    // Lógica para verificar disponibilidad en la base de datos
    const query = `
        SELECT COUNT(*) AS count FROM reservas
        WHERE fecha = ? AND hora = ? AND cancha = ?
    `;

    connection.query(query, [fecha, hora, cancha], (err, results) => {
        if (err) {
            console.error('Error al verificar disponibilidad:', err);
            res.status(500).send('Error al verificar disponibilidad');
            return;
        }

        const disponible = results[0].count === 0; // Si no hay reservas, está disponible
        res.status(200).json({ disponible });
    });
});


// Ruta para guardar una reserva
app.post('/reservar', (req, res) => {
    const { nombre, telefono, correo, fecha, hora, cancha } = req.body;

    const query = `
        INSERT INTO reservas (nombre, telefono, correo, fecha, hora, cancha)
        VALUES (?, ?, ?, ?, ?, ?)
    `;

    connection.query(query, [nombre, telefono, correo, fecha, hora, cancha], (err, results) => {
        if (err) {
            console.error('Error al guardar la reserva:', err);
            res.status(500).send('Error al guardar la reserva');
            return;
        }
        res.status(200).send('Reserva guardada correctamente');
    });
});

// Iniciar el servidor
app.listen(port, () => {
    console.log(`Servidor backend corriendo en http://localhost:${port}`);
});