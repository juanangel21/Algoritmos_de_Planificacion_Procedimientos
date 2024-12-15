import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public class PlanificadorProcesos {

    // Clase para representar un proceso
    static class Proceso {
        String id;
        int llegada;
        int duracion;

        public Proceso(String id, int llegada, int duracion) {
            this.id = id;
            this.llegada = llegada;
            this.duracion = duracion;
        }
    }

    // Método para leer procesos desde un archivo CSV
    public static List<Proceso> leerProcesos(String archivoCsv) throws IOException {
        List<Proceso> procesos = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(archivoCsv));
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(",");
            procesos.add(new Proceso(datos[0], Integer.parseInt(datos[1]), Integer.parseInt(datos[2])));
        }

        br.close();
        return procesos;
    }

    // Algoritmo Round Robin
    public static List<String> roundRobin(List<Proceso> procesos, int quantum) {
        int tiempo = 0;
        Queue<Proceso> cola = new LinkedList<>();
        List<String> secuencia = new ArrayList<>();
        List<Proceso> procesosOrdenados = new ArrayList<>(procesos);
        procesosOrdenados.sort(Comparator.comparingInt(p -> p.llegada));
        int index = 0;

        while (!cola.isEmpty() || index < procesosOrdenados.size()) {
            while (index < procesosOrdenados.size() && procesosOrdenados.get(index).llegada <= tiempo) {
                cola.add(procesosOrdenados.get(index));
                index++;
            }

            if (!cola.isEmpty()) {
                Proceso proceso = cola.poll();
                int ejecucion = Math.min(proceso.duracion, quantum);
                proceso.duracion -= ejecucion;
                tiempo += ejecucion;

                for (int i = 0; i < ejecucion; i++) {
                    secuencia.add(proceso.id);
                }

                while (index < procesosOrdenados.size() && procesosOrdenados.get(index).llegada <= tiempo) {
                    cola.add(procesosOrdenados.get(index));
                    index++;
                }
                if (proceso.duracion > 0) {
                    cola.add(proceso);
                }
            } else {
                tiempo++;
            }
        }

        return secuencia;
    }

    // Algoritmo Shortest Job First (SJF) no preventivo
    public static List<String> sjfNoPreemptivo(List<Proceso> procesos) {
        int tiempo = 0;
        List<String> secuencia = new ArrayList<>();
        List<Proceso> procesosOrdenados = new ArrayList<>(procesos);
        procesosOrdenados.sort(Comparator.comparingInt((Proceso p) -> p.llegada).thenComparingInt(p -> p.duracion));
        List<Proceso> pendientes = new ArrayList<>();

        while (!procesosOrdenados.isEmpty() || !pendientes.isEmpty()) {
            while (!procesosOrdenados.isEmpty() && procesosOrdenados.get(0).llegada <= tiempo) {
                pendientes.add(procesosOrdenados.remove(0));
            }

            if (!pendientes.isEmpty()) {
                pendientes.sort(Comparator.comparingInt(p -> p.duracion));
                Proceso proceso = pendientes.remove(0);
                for (int i = 0; i < proceso.duracion; i++) {
                    secuencia.add(proceso.id);
                }
                tiempo += proceso.duracion;
            } else {
                tiempo++;
            }
        }

        return secuencia;
    }

    // Método para abrir un popup y cargar un archivo CSV con soporte multiplataforma
    public static String cargarArchivoCSV() {
        while (true) { // Bucle para permitir reintento hasta que se seleccione un archivo válido
            String sistemaOperativo = System.getProperty("os.name").toLowerCase();
            File archivoSeleccionado;

            if (sistemaOperativo.contains("mac")) {
                // Usar FileDialog para macOS
                FileDialog fileDialog = new FileDialog((Frame) null, "Seleccione un archivo CSV", FileDialog.LOAD);
                fileDialog.setFilenameFilter((dir, name) -> name.toLowerCase().endsWith(".csv")); // Filtro para archivos .csv
                fileDialog.setVisible(true);

                String archivo = fileDialog.getFile();
                String directorio = fileDialog.getDirectory();

                if (archivo == null || directorio == null) {
                    JOptionPane.showMessageDialog(null, "No se seleccionó ningún archivo. Finalizando operación.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
                archivoSeleccionado = new File(directorio, archivo);
            } else {
                // Usar JFileChooser para Windows y Linux
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccione un archivo CSV");
                FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV", "csv");
                fileChooser.setFileFilter(filtro);

                int seleccion = fileChooser.showOpenDialog(null);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    archivoSeleccionado = fileChooser.getSelectedFile();
                } else {
                    JOptionPane.showMessageDialog(null, "No se seleccionó ningún archivo. Finalizando operación.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
            }

            // Validar que el archivo tenga extensión .csv
            if (!archivoSeleccionado.getName().toLowerCase().endsWith(".csv")) {
                JOptionPane.showMessageDialog(null, "El archivo seleccionado no es un archivo CSV. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Volver a mostrar el diálogo de selección
            }

            // Copiar el archivo seleccionado a una carpeta dentro del proyecto
            String destinoDirectorio = "datos"; // Carpeta dentro del proyecto
            File directorio = new File(destinoDirectorio);
            if (!directorio.exists()) {
                directorio.mkdir(); // Crear carpeta si no existe
            }

            File destino = new File(directorio, archivoSeleccionado.getName());
            try {
                Files.copy(archivoSeleccionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Archivo cargado exitosamente en: " + destino.getAbsolutePath());
                return destino.getAbsolutePath();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al copiar el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para mostrar un diálogo de confirmación antes de solicitar el archivo
    public static boolean solicitarConfirmacion() {
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "Se solicitará un archivo CSV. ¿Desea continuar?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );
        return opcion == JOptionPane.YES_OPTION;
    }

    // Ejecución principal
    public static void main(String[] args) throws IOException {
        // Mostrar diálogo de confirmación
        if (!solicitarConfirmacion()) {
            System.out.println("El usuario canceló la operación. Finalizando programa...");
            return;
        }

        // Cargar archivo CSV a través de un popup
        String archivoCsv = cargarArchivoCSV();
        if (archivoCsv == null) {
            System.out.println("Finalizando programa debido a la falta de archivo.");
            return;
        }

        // Leer los procesos
        List<Proceso> procesos = leerProcesos(archivoCsv);

        Scanner scanner = new Scanner(System.in);

        // Ejecutar Round Robin
        System.out.println("Ingrese el valor del quantum (q) para Round Robin:");
        int quantum = scanner.nextInt();
        List<String> secuenciaRR = roundRobin(procesos, quantum);
        System.out.println("\nSecuencia de ejecución (Round Robin):");
        System.out.println(String.join(",", secuenciaRR));

        // Ejecutar SJF No Preventivo
        procesos = leerProcesos(archivoCsv); // Volver a cargar los procesos, ya que se modificaron
        List<String> secuenciaSJF = sjfNoPreemptivo(procesos);
        System.out.println("\nSecuencia de ejecución (SJF No Preventivo):");
        System.out.println(String.join(",", secuenciaSJF));
    }
}
