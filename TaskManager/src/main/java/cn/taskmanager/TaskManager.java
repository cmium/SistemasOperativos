package cn.taskmanager;
import java.util.*;
/**
 * @author Carlos_Nimacache
 */

// Clase Tareas
class Task {
    String name;
    int priority;
    boolean completed;

    public Task(String name, int priority) {
            
        this.name = name;
        this.priority = priority;
        this.completed = false;
    }
}

// Clase Usuarios
class User {
    String name;
    Queue<Task> taskQueue;

    public User(String name) {
        this.name = name;
        this.taskQueue = new LinkedList<>();
    }
}

// Clase administración de tareas
public class TaskManager {
    Queue<Task> fifoQueue = new LinkedList<>();
    PriorityQueue<Task> priorityQueue = new PriorityQueue<>((t1, t2) -> Integer.compare(t2.priority, t1.priority));
    List<User> users = new ArrayList<>();
    int roundRobinIndex = 0;

    // Método para agregar un usuario a la lista de usuarios
    public void addUser(String userName) {
        users.add(new User(userName));
    }

    // Método para agregar una tarea a la cola de tareas del usuario y a las colas FIFO y de prioridad
    public void addTask(String userName, String taskName, int priority) {
        User user = getUser(userName);
        Task task = new Task(taskName, priority);
        user.taskQueue.add(task);
        fifoQueue.add(task);
        priorityQueue.add(task);
    }

    // Método para completar una tarea en los tres algoritmos de planificación
    public void completeTaskInAllAlgorithms() {
        for (User user : users) {
            if (!user.taskQueue.isEmpty()) {
                Task task = user.taskQueue.poll();
                task.completed = true;
                System.out.println("-FIFO tarea completada para " + user.name + ": " + task.name);
            }
        }
        Task priorityTask = priorityQueue.poll();
        priorityTask.completed = true;
        System.out.println("-Prioridad tarea completada: " + priorityTask.name);

        User roundRobinUser = users.get(roundRobinIndex);
        roundRobinIndex = (roundRobinIndex + 1) % users.size();
        if (!roundRobinUser.taskQueue.isEmpty()) {
            Task roundRobinTask = roundRobinUser.taskQueue.poll();
            roundRobinTask.completed = true;
            System.out.println("-Round Robin tarea completada para " + roundRobinUser.name + ": " + roundRobinTask.name);
        }
    }

    // Método privado para obtener un usuario por nombre
    private User getUser(String userName) {
        for (User user : users) {
            if (user.name.equals(userName)) {
                return user;
            }
        }
        throw new IllegalArgumentException("Usuario no encontrado " + userName);
    }

    // Método para imprimir el estado de las tareas
    public void printTaskStatus() {
        System.out.println("Estado de Tareas");
        for (User user : users) {
            System.out.println("Usuario: " + user.name);
            for (Task task : user.taskQueue) {
                System.out.println("- Tarea: " + task.name + " (Prioridad: " + task.priority + ", Completa: " + task.completed + ")");
            }
        }
    }
// Método para mostrar la tabla de procesos con encabezado e información específica
public static void mostrarTablaDeProcesos() {
    System.out.println("Tabla de Procesos");
    System.out.printf("%-10s %-20s %-20s %-20s%n", "Proceso", "Consumo de CPU (ms)", "Consumo de E/S (ms)", "Tiempo de Llegada (ms)");
    System.out.printf("%-10s %-20d %-20d %-20d%n", "P1", 20, 5, 0);
    System.out.printf("%-10s %-20d %-20d %-20d%n", "P2", 15, 10, 5);
    System.out.printf("%-10s %-20d %-20d %-20d%n", "P3", 30, 5, 10);
    System.out.printf("%-10s %-20d %-20d %-20d%n", "P4", 10, 20, 15);
    System.out.printf("%-10s %-20d %-20d %-20d%n", "P5", 25, 15, 20);
}



    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - ");
            System.out.println("1. Agregar Usuario");
            System.out.println("2. Agregar Tarea");
            System.out.println("3. Completar Tarea en todos los algoritmos");
            System.out.println("4. Mostrar Estado de Tareas");
            System.out.println("5. Mostrar Tabla de Procesos"); // Nueva opción
            System.out.println("6. Salir");

            System.out.print("\n>  ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Ingresar nombre de usuario: ");
                    String newUserName = scanner.nextLine();
                    taskManager.addUser(newUserName);
                    break;
                case 2:
                    System.out.print("Ingresar nombre de usuario: ");
                    String userName = scanner.nextLine();
                    System.out.print("Ingresar nombre de la tarea: ");
                    String taskName = scanner.nextLine();
                    System.out.print("Ingresar prioridad de la tarea: ");
                    int priority = scanner.nextInt();
                    taskManager.addTask(userName, taskName, priority);
                    break;
                case 3:
                    // Antes de realizar el cambio de contexto
                    long startTime = System.currentTimeMillis();
                    taskManager.completeTaskInAllAlgorithms();
                    // Después de realizar el cambio de contexto
                    long endTime = System.currentTimeMillis();
                    // Calcula la duración en milisegundos
                    long durationInMillis = endTime - startTime;
                    System.out.println("Tiempo de ejecución en todos los algoritmos: " + durationInMillis + " milisegundos");
                    break;
                case 4:
                    taskManager.printTaskStatus();
                    break;
                case 5:
                    TaskManager.mostrarTablaDeProcesos();
                    break;

                case 6:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("ERROR, Ingresa una opción válida.");
            }
        }
    }
}

