# Development Env
* Ubuntu 20.04.3
* java openjdk 11.0.19 (Released on 2023-04-18)
* javac 11.0.19
* Gradle 8.1.1


# Assumptions
* It is assumed that the "Cumulative CO2 Emission Start" value of rule number 3 is incorrect. It should be equal to the "Cumulative CO2 Emission End" value of rule number 2, which is 600 kg.
* Therefore, the correct rules should be as follows:

| Rule Range | Start | End | Increasing Percentage |
|------------|-------|-----|-----------------------|
| 1          | 0     | 380 | 100%                  |
| 2          | 380   | 600 | 120%                  |
| 3        |_**600**_| -   | 150%                  |


# Program Input and Output
## input
### 1. expected emissions values
* place the expected emissions values in the file at  
`_app/src/main/resources/testcase/baseValues.json_`
* format of expected emissions values  
**[value1, value2, value3, value4, valueN]**  
for example:  
**[200, 180, 120, 150, 220]**

### 2. cumulative CO2 emission rule
* place the cumulative CO2 emission rule in the file at  
`_app/src/main/resources/testcase/rules.json_`
* rules must be written in **a single line**
* the _**end**_ of the last rule **must be -1**
* the cumulative CO2 emission rule is written in json format 
**[{"start":_number_,"end":_number_,"percent":_number_},{"start":_number_,"end":_number_,"percent":_number_}]**  
for example:  
**[{"start":0,"end":380,"percent":100},{"start":380,"end":-1,"percent":120}]**  
* rules must be written in order from low range to high range
## output
The anticipated CO2 emission values will be printed in the terminal.

# Run Program
1. Open a terminal and navigate to the CO2EmissionAssumption directory.
2. Place the input files as mentioned above.
3. Run the command:   
* For Linux-based OS  
`./gradlew run`
* For Windows  
`.\gradlew.bat run`
4. View the output in the terminal.

# Run Test
1. Open a terminal and navigate to the CO2EmissionAssumption directory.
2. place **expected emissions values** and **cumulative CO2 emission rule** as mentioned above, but change the file path to  
`_app/src/test/resources/testcase1/baseValues.json_`  
and  
`_app/src/test/resources/testcase1/rules.json_`  
respectively  
3. place **the correct anticipated CO2 emission values** into the file at 
`_app/src/test/resources/testcase1/expectedAnticipatedValues.json_`  
using the same format as **expected emissions values**
3. Run the command:  
* For Linux-based OS  
`./gradlew test`
* For Windows  
`.\gradlew.bat test`
4. If the output from the program matches the **correct anticipated CO2 emission** values, you will see the text **BUILD SUCCESSFUL** in the terminal.

