
Problema de secuenciación de tareas en máquinas paralelas con objetivo de latencia
Se dispone de un conjunto de m máquinas paralelas idénticas, en las que
hay que realizar n tareas. Se deben secuenciar las n tareas, con tiempos de
procesamiento p n , en las m máquinas, de de tal manera que se minimice
la latencia (tiempo total de permanencia en el sistema). Cada tarea n de-
be realizarse una única vez en cualquiera de las m máquinas. Por ejemplo,
una solución para el problema con 10 tareas con tiempos de procesamiento
t1(2),t2(3), t3(5), t4(8), t5(1), t6(4), t7(6), t8(9), t9(10), t10(4) y 2 máqui-
nas, podrı́a ser la siguiente: Máquina 1 - tareas 9, 10, 8 y 6; Máquina 2 -
tareas 3, 5, 4, 7, 1 y 2; con una latencia de 92 (latencia máquina 1 = (10 +
(10+4) + (10+4+9) + (10+4+9+4)) = 74; latencia máquina 2 = (5 + (5+1)
+ (5+1+8) + (5+1+8+6) + (5+1+8+6+2) + (5+1+8+6+2+3)) = 92).
Los algoritmos deben poder resolver cuanquier instancia con cualquier
número de tareas, n, y cualquier número de máquinas m.
Leer la descripción del problema Vehicle Routing Problem con objetivo
de latencia para definir la función objetivo.
Las tareas a realizar para el desarrollo de este proyecto son las siguientes:


1. Buscar información del problema, instancias de prueba de la literatura
-- ftp://tesis.bbtk.ull.es/ccppytec/cp3.pdf
-- Algoritmo genetico: https://www.sciencedirect.com/science/article/pii/S1405774313722248

2. y resultados de la literatura cientı́fica con los que comparar (grupo completo).

2. Definir la codificación de las soluciones (grupo completo).

3. Definir e implementar, al menos, cuatro estructuras de entorno diferentes(re-inserción,
   intercambio intra y entre rutas, 2-opt) (grupo completo).

4. Diseñar e implementar un algoritmo constructivo determinista y un
GRASP.

5. Diseñar e implementar un algoritmo multiarranque con soluciones alea-
torias de comienzo.

6. Diseñar e implementar un argoritmo de búsqueda por entornos varia-
bles (VNS).

7. Diseñar e implementar un algoritmo de búsqueda tabú (TS).

8. Diseñar e implementar un algoritmo de búsqueda por entornos grandes
(LNS).

9. Redactar el informe en latex (https://www.overleaf.com/15253832sqvvzrxxqtkj)

10. Realizar una defensa parcial, el martes 24 de abril de 2018.

11. Defender el proyecto en el dı́a asignado (dos últimas semanas de clase).

NOTA IMPORTANTE. El martes, 24 de abril, se deberá defender y
explicar el trabajo realizado hasta ese momento (incluido el informe). De-
ben haberse concluido los apartados de 1 al 4, incluida las partes asociadas
del informe. Además, debe tenerse un primer borrador de los algoritmos
a implementar, ası́ como las notas asiciadas incluidas en el informe. El no
cumplimiento de este requerimiento, podrı́a suponer la no superación de la
práctica final. Se habilitará una tarea para la subida del código parcial.