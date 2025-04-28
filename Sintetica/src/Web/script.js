// Mostrar el formulario de reserva al hacer clic en "Reservar"
document.querySelectorAll('.reservar-btn').forEach(button => {
    button.addEventListener('click', () => {
        // Obtener el tipo de cancha desde el atributo data-cancha
        const tipoCancha = button.getAttribute('data-cancha');

        // Actualizar el título de la sección de reserva
        const tituloReserva = document.getElementById('titulo-reserva');
        tituloReserva.textContent = `Reserva tu Cancha - ${tipoCancha}`;

        // Ocultar la sección de canchas
        document.getElementById('canchas').classList.add('hidden');

        // Mostrar el formulario de reserva
        document.getElementById('formulario-reserva').classList.remove('hidden');
    });
});

// Verificar disponibilidad
document.getElementById('verificar-disponibilidad').addEventListener('click', () => {
    const fecha = document.getElementById('fecha').value;
    const hora = document.getElementById('hora').value;

    // Simular verificación de disponibilidad (puedes reemplazar esto con una llamada al backend)
    const disponible = true; // Cambia a false para simular que no hay disponibilidad

    if (disponible) {
        // Mostrar los campos de datos del cliente
        document.getElementById('datos-cliente').classList.remove('hidden');
    } else {
        alert("❌ No hay disponibilidad para la fecha y hora seleccionadas.");
    }
});

// Enviar el formulario de reserva
document.getElementById('reserva-form').addEventListener('submit', (e) => {
    e.preventDefault();
    const nombre = document.getElementById('nombre').value;
    const telefono = document.getElementById('telefono').value;
    const correo = document.getElementById('correo').value;

    alert(`✅ Reserva confirmada para ${nombre}. Te contactaremos al ${telefono} o ${correo}.`);
});