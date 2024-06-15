package com.civilo.roller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseBackupScheduler {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseBackupScheduler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Método para ejecutar la tarea de respaldo cada 1 hora
    @Scheduled(fixedRate = 3600000) // 1 hora = 3,600,000 milisegundos
    public void backupDatabase() {
        try {
            // Ruta de la carpeta "Documentos" del sistema
            String documentsFolderPath = System.getProperty("user.home") + File.separator + "Documents";

            // Crear la carpeta "backup" dentro de la carpeta "Documentos" si no existe
            File backupFolder = new File(documentsFolderPath, "backup");
            if (!backupFolder.exists()) {
                backupFolder.mkdirs();
            }

            // Obtener la fecha y hora actual para el nombre del archivo de respaldo
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
            String timestamp = sdf.format(new Date());

            // Crear la carpeta con el formato de fecha "DD-MM-YYYY_HH-MM" dentro de la carpeta "backup"
            File timestampFolder = new File(backupFolder, timestamp);
            if (!timestampFolder.exists()) {
                timestampFolder.mkdirs();
            }

            // Obtener la lista de tablas en la base de datos
            List<String> tableNames = jdbcTemplate.queryForList(
                    "SELECT table_name FROM information_schema.tables WHERE table_schema = 'civilo_roller_db'",
                    String.class
            );

            // Generar el archivo SQL de respaldo por cada tabla dentro de la carpeta con la fecha
            for (String tableName : tableNames) {
                String backupFileName = "backup_" + tableName + "_" + timestamp + ".sql";
                File backupFile = new File(timestampFolder, backupFileName);
                try (FileWriter fileWriter = new FileWriter(backupFile)) {
                    List<Map<String, Object>> dataList = jdbcTemplate.queryForList("SELECT * FROM " + tableName);
                    for (Map<String, Object> data : dataList) {
                        StringBuilder rowBuilder = new StringBuilder();
                        for (Object value : data.values()) {
                            rowBuilder.append(value).append(",");
                        }
                        rowBuilder.deleteCharAt(rowBuilder.length() - 1);
                        fileWriter.write(rowBuilder.toString() + "\n");
                    }
                }
                System.out.println("Respaldo creado exitosamente: " + backupFileName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}