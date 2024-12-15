# Planificador de Procesos

Este programa implementa algoritmos de planificación de procesos, como **Round Robin** y **Shortest Job First (SJF)** no preventivo. Es compatible con macOS, Windows y Linux.

---

## **Requisitos previos**
Antes de ejecutar el programa, asegúrate de cumplir con los siguientes requisitos:

1. **Java Development Kit (JDK):**
   - Versión mínima recomendada: **JDK 8**.
   - Descárgalo desde [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) o una alternativa como [AdoptOpenJDK](https://adoptopenjdk.net/).

2. **Editor o IDE:**
   - Usa un editor de texto o IDE como:
      - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
      - [Eclipse IDE](https://www.eclipse.org/eclipseide/)
      - [Visual Studio Code](https://code.visualstudio.com/) con extensión de Java.

3. **Archivo CSV:**
   - El archivo CSV debe contener información de procesos en el siguiente formato:
     ```
     id,llegada,duracion
     P1,0,5
     P2,1,3
     P3,2,8
     ```

---

## **Cómo ejecutar el programa**

### **1. Clonar el repositorio**
Clona este repositorio en tu computadora usando el siguiente comando:

```bash
git clone https://github.com/tu_usuario/planificador-procesos.git
cd planificador-procesos
```

---

### **2. Compilar el programa**
En la terminal, navega al directorio del repositorio y ejecuta el siguiente comando para compilar el programa:

```bash
javac PlanificadorProcesos.java
```

Esto generará el archivo `PlanificadorProcesos.class`.

---

### **3. Ejecutar el programa**
Ejecuta el programa con el siguiente comando:

```bash
java PlanificadorProcesos
```

El programa mostrará un mensaje de confirmación:

```
Se solicitará un archivo CSV. ¿Desea continuar?
```

- Haz clic en **Sí** para continuar.
- Si haces clic en **No**, el programa finalizará.

---

### **4. Seleccionar el archivo CSV**
- Aparecerá un cuadro de diálogo para seleccionar un archivo CSV.
- Busca y selecciona el archivo que contiene los datos de procesos.

#### **Notas:**
- En **macOS**, el cuadro de diálogo será nativo.
- En **Windows** y **Linux**, se usará un cuadro de diálogo genérico.
- Si seleccionas un archivo con una extensión diferente a `.csv`, el programa mostrará un mensaje de error y te permitirá intentarlo nuevamente.

---

### **5. Ingresar los parámetros**
El programa te pedirá que ingreses el valor del **quantum** para el algoritmo Round Robin. Por ejemplo:

```
Ingrese el valor del quantum (q) para Round Robin:
2
```

Tras ingresar el quantum, el programa ejecutará los algoritmos **Round Robin** y **SJF No Preventivo**.

---

### **6. Salida esperada**
El programa imprimirá la secuencia de ejecución de los procesos en la consola. Por ejemplo:

```
Secuencia de ejecución (Round Robin):
P1,P1,P2,P2,P1,P1,P3,P3,P3,P3,P3,P3

Secuencia de ejecución (SJF No Preventivo):
P1,P1,P1,P1,P1,P2,P2,P2,P3,P3,P3,P3,P3,P3,P3,P3
```

---

## **Formato del archivo CSV**

El archivo CSV debe seguir este formato:

```
id,llegada,duracion
P1,0,5
P2,1,3
P3,2,8
```

---

## **Solución de problemas**

### **1. Error al compilar**
Si el comando `javac` no funciona, asegúrate de que el JDK esté correctamente instalado y configurado en tu sistema. Verifica ejecutando:

```bash
javac -version
```

Si no tienes el JDK instalado, descárgalo e instálalo desde [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html).

---

### **2. No aparece el cuadro de diálogo**
En **macOS**, verifica que la terminal o el IDE tenga permisos para mostrar ventanas gráficas. Ve a:

```
System Preferences > Security & Privacy > Accessibility
```

Y asegúrate de que tu terminal o IDE esté habilitado.

---

### **3. Error de archivo no válido**
Asegúrate de que el archivo tiene la extensión `.csv` y sigue el formato correcto descrito anteriormente.

---
