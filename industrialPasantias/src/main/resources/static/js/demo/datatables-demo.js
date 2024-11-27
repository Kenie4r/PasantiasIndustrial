// Call the dataTables jQuery plugin
$(document).ready(function () {
  $('#dataTable').DataTable({
    "paging": true,
    "searching": true,
    "ordering": true,
    "pageLength": 10,
    "language": {
      "url": "/js/datatables/Spanish.json"  // Ruta local
    }
  });
});
