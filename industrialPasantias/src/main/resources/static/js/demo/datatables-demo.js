// Call the dataTables jQuery plugin
$(document).ready(function () {
  $('#dataTable').DataTable({
    // Opciones básicas
    "paging": true,
    "lengthMenu": [5, 10, 25],
    "searching": true,
    "ordering": true,
    "info": true,
    "autoWidth": false,
    "language": {
      "url": "/js/datatables/Spanish.json"  // Ruta local
    },
  });
});
