// Mostrar el formulario de reserva al hacer clic en "Reservar"
document.querySelectorAll('.reservar-btn').forEach(button => {
    button.addEventListener('click', () => {
        const tipoCancha = button.getAttribute('data-cancha');
        document.getElementById('titulo-reserva').textContent = `Reserva tu Cancha - ${tipoCancha}`;
        document.getElementById('cancha').value = tipoCancha;
        document.getElementById('canchas').classList.add('hidden');
        document.getElementById('formulario-reserva').classList.remove('hidden');
    });
});

// Mostrar calendario al hacer clic en el campo de fecha (compatibilidad con navegadores modernos)
const fechaInput = document.getElementById('fecha');
fechaInput.addEventListener('click', function() {
    this.showPicker && this.showPicker();
});

// Verificar disponibilidad
document.getElementById('verificar-btn').addEventListener('click', () => {
    const fecha = document.getElementById('fecha').value;
    const hora = document.getElementById('hora').value;
    const cancha = document.getElementById('cancha').value;

    if (!fecha || !hora || !cancha) {
        alert("Por favor completa todos los campos antes de verificar la disponibilidad.");
        return;
    }

    console.log("Datos enviados al servidor:", { fecha, hora, cancha });


    fetch('http://localhost:3002/verificar-disponibilidad', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ fecha, hora, cancha })
    })
    .then(response => response.json())
    .then(data => {
        if (data.disponible) {
            alert("✅ La cancha está disponible en la fecha y hora seleccionadas.");
        } else {
            alert("❌ La cancha NO está disponible en la fecha y hora seleccionadas.");
        }
    })
    .catch(error => {
        console.error("Error al verificar disponibilidad:", error);
        alert("❌ Hubo un error al verificar la disponibilidad");
    });
});

// Enviar el formulario de reserva
document.getElementById('reserva-form').addEventListener('submit', (e) => {
    e.preventDefault();

    const nombre = document.getElementById('nombre').value;
    const telefono = document.getElementById('telefono').value;
    const correo = document.getElementById('correo').value;
    const fecha = document.getElementById('fecha').value;
    const hora = document.getElementById('hora').value;
    const cancha = document.getElementById('cancha').value;

    if (!nombre || !telefono || !correo || !fecha || !hora || !cancha) {
        alert("❌ Por favor, completa todos los campos.");
        return;
    }

    const reserva = { nombre, telefono, correo, fecha, hora, cancha };

    fetch('http://localhost:3002/reservar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reserva)
    })
    .then(response => response.text())
    .then(message => {
        alert(`✅ ${message}`);
        document.getElementById('reserva-form').reset();
        document.getElementById('formulario-reserva').classList.add('hidden');
        document.getElementById('canchas').classList.remove('hidden');
    })
    .catch(error => {
        console.error('Error:', error);
        alert('❌ Hubo un error al guardar la reserva. Por favor, intenta nuevamente.');
    });
});
