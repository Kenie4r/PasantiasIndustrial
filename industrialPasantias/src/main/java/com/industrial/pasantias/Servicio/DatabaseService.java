package com.industrial.pasantias.Servicio;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import javax.sql.DataSource;

@Service
public class DatabaseService {
    @Autowired
    private DataSource dataSource;

    private String backupPath = "C:\\pasantias\\backups\\CONTROL_PASANTIAS_INDUSTRIAL_V3";

    public void backupDatabase() throws SQLException, IOException {
        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestampFecha = LocalDateTime.now().format(formatterFecha);

        String backupCommand = "BACKUP DATABASE CONTROL_PASANTIAS_INDUSTRIAL_V3 TO DISK = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(backupCommand)) {
            preparedStatement.setString(1, backupPath + "_" + timestampFecha + ".bak");
            preparedStatement.execute();
        }
    }

    /*
     * --------------------------
     * RESTORE cannot process database 'CONTROL_PASANTIAS_INDUSTRIAL_V3' because it
     * is in use by this session. It is recommended that the master database be used
     * when performing this operation.
     * --------------------------
     * public void restoreDatabase(String backupFilePath) throws SQLException {
     * String databaseName = "CONTROL_PASANTIAS_INDUSTRIAL_V3";
     * 
     * try (Connection connection = dataSource.getConnection()) {
     * // Cambiar el contexto a la base de datos 'master'
     * try (Statement stmt = connection.createStatement()) {
     * // Poner la base de datos en modo SINGLE_USER para cerrar conexiones activas
     * //stmt.execute("ALTER DATABASE " + databaseName +
     * " SET SINGLE_USER WITH ROLLBACK IMMEDIATE");
     * 
     * // Ejecutar el comando de restauración
     * String restoreCommand = "RESTORE DATABASE " + databaseName +
     * " FROM DISK = ? WITH REPLACE, RECOVERY";
     * try (PreparedStatement pstmt = connection.prepareStatement(restoreCommand)) {
     * pstmt.setString(1, backupFilePath);
     * pstmt.execute();
     * }
     * 
     * // Volver a poner la base de datos en modo MULTI_USER
     * //stmt.execute("ALTER DATABASE " + databaseName + " SET MULTI_USER");
     * 
     * System.out.
     * println("backup Restauración ejecutada correctamente para el archivo: " +
     * backupFilePath);
     * }
     * } catch (SQLException e) {
     * System.err.println("Error al ejecutar el comando de restauración: backup" +
     * e.getMessage());
     * throw e;
     * }
     * }
     */
}
