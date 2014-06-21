JFLAGS = -g
JC = javac
JVM = java
Applet = appletviewer
JDOC = javadoc -d 
JAR = jar
FILE=
.SUFFIXES: .java .class .html .jar
.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
		PhysicsLab.java \
        Ball.java \
        BallView.java \
        FixedHook.java \
        FixedHookView.java \
        Grafic.java \
        LabMenuListener.java \
        MouseListener.java \
        MyWorld.java \
        MyWorldView.java \
        NextKeyListener.java \
        Oscillator.java \
        OscillatorView.java \
        PhysicsElement.java \
        PhysicsLabApplet.java \
        Simulateable.java \
        Spring.java \
        SpringAttachable.java \
        SpringView.java

MAIN = PhysicsLab
MAINApplet = PhysicsLabApplet
MAINC = $(MAIN).class

.PHONY: all run clean doc jar

all: $(CLASSES:.java=.class)

run: $(MAIN).class 
	$(JVM) -jar $(MAIN).jar $(FILE)
	
runApplet: $(MAINApplet).class 
	$(Applet) $(MAIN).html $(FILE)
	
clean:
	$(RM) *.class

jar: 
	$(JAR) cmf PhysicsLabFile PhysicsLab.jar *.class org

doc:
	$(JDOC) ./documentation *.java		
