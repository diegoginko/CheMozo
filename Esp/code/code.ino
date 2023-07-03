#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include <PubSubClient.h>

const char* ssid = "****";
const char* password = "****";

//Led interno 8266 cambiar a 2 para esp01
const int ledInterno = 16;

const int btnUno = 4;
const int btnDos = 5;
int btnUnoPressed = 0;
int btnDosPressed = 0;

WiFiUDP Udp;
unsigned int localUdpPort = 7234;  // local port to listen on
IPAddress broadcast=IPAddress(239, 1, 1, 234);
char incomingPacket[255];  // buffer for incoming packets
char  replyPacket[] = "Hi there! Got the message :-)";  // a reply string to send back

// MQTT Broker
//const char *mqtt_broker = "broker.emqx.io";
String topic = "mesa/accion/";
String keepAlive = "mesa/keepAlive/";
const char *mqtt_username = "user";
const char *mqtt_password = "public";
const int mqtt_port = 1883;

WiFiClient espClient;
PubSubClient client(espClient);


void setup()
{
  Serial.begin(115200);
  Serial.println();
  pinMode(btnUno, INPUT);
  pinMode(btnDos, INPUT);
  pinMode(ledInterno, OUTPUT);

  Serial.printf("Connecting to %s ", ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED)
  {
    digitalWrite(ledInterno, HIGH);
    delay(500);
    digitalWrite(ledInterno, LOW);
    Serial.print(".");
  }
  Serial.println(" connected");

  //Udp.begin(localUdpPort);
  Udp.beginMulticast(WiFi.localIP(), broadcast,localUdpPort);
  Serial.printf("Now listening at IP %s, UDP port %d\n", WiFi.localIP().toString().c_str(), localUdpPort);
  
  bool handShake = false;
  while(!handShake){
    Serial.println("Esperando ip");
    digitalWrite(ledInterno, LOW);
    int packetSize = Udp.parsePacket();
    if (packetSize)
    {
      // receive incoming UDP packets
      Serial.printf("Received %d bytes from %s, port %d\n", packetSize, Udp.remoteIP().toString().c_str(), Udp.remotePort());
      int len = Udp.read(incomingPacket, 255);
      if (len > 0)
      {
        incomingPacket[len] = 0;
      }
      Serial.printf("UDP packet contents: %s\n", incomingPacket);

      char* mqtt_broker = incomingPacket;

      //connecting to a mqtt broker
      client.setServer(mqtt_broker, mqtt_port);
      client.setCallback(callback);
      while (!client.connected()) {
          String client_id = "CheMozo-";
          client_id += String(WiFi.macAddress());
          Serial.printf("The client %s connects to the public mqtt broker\n", client_id.c_str());
          if (client.connect(client_id.c_str(), mqtt_username, mqtt_password)) {
              Serial.println("Public mqtt broker connected");
          } else {
              Serial.print("failed with state ");
              Serial.print(client.state());
              delay(2000);
          }
      }
      // publish and subscribe
      String macAdress = String(WiFi.macAddress());
      keepAlive += macAdress.c_str();
      topic += macAdress.c_str();
      client.publish(topic.c_str(), "Hola!!", macAdress.c_str());
      client.subscribe(topic.c_str());
      digitalWrite(ledInterno, HIGH);

      handShake = true;
    }
    
    
  }
}

void callback(char *topic, byte *payload, unsigned int length) {
  Serial.print("Message arrived in topic: ");
  Serial.println(topic);
  Serial.print("Message:");
  for (int i = 0; i < length; i++) {
      Serial.print((char) payload[i]);
  }
  Serial.println();
  Serial.println("-----------------------");
}


void loop()
{
  btnUnoPressed = digitalRead(btnUno);
  btnDosPressed = digitalRead(btnDos);

  client.publish(keepAlive.c_str(),"Estoy vivo");

  if(!client.connected()){
    Serial.println("Desconectado de MQTT");
    digitalWrite(ledInterno, LOW);
  }

  if(btnUnoPressed == HIGH || btnDosPressed == HIGH){
    if(btnUnoPressed == HIGH && btnDosPressed == HIGH){
      client.publish(topic.c_str(),"Los dos");
      digitalWrite(ledInterno, LOW);
      delay(1000);
      digitalWrite(ledInterno, HIGH);
    }else if(btnUnoPressed == HIGH){
      client.publish(topic.c_str(),"Uno");
      digitalWrite(ledInterno, LOW);
      delay(1000);
      digitalWrite(ledInterno, HIGH);
    }else if(btnDosPressed == HIGH){
      client.publish(topic.c_str(),"Dos");
      digitalWrite(ledInterno, LOW);
      delay(1000);
      digitalWrite(ledInterno, HIGH);
    }else{
      client.publish(topic.c_str(),"No se");
      digitalWrite(ledInterno, LOW);
      delay(1000);
      digitalWrite(ledInterno, HIGH);
    }
  }
  digitalWrite(btnUnoPressed,LOW);
  digitalWrite(btnDosPressed,LOW);
}