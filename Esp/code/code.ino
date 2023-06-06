#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include <PubSubClient.h>

const char* ssid = "****";
const char* password = "****";

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
const char *topic = "esp8266/test";
const char *mqtt_username = "emqx";
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

  Serial.printf("Connecting to %s ", ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }
  Serial.println(" connected");

  //Udp.begin(localUdpPort);
  Udp.beginMulticast(WiFi.localIP(), broadcast,localUdpPort);
  Serial.printf("Now listening at IP %s, UDP port %d\n", WiFi.localIP().toString().c_str(), localUdpPort);
  
  bool handShake = false;
  while(!handShake){
    Serial.println("Esperando ip");
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
          String client_id = "esp8266-client-";
          client_id += String(WiFi.macAddress());
          Serial.printf("The client %s connects to the public mqtt broker\n", client_id.c_str());
          if (client.connect(client_id.c_str(), mqtt_username, mqtt_password)) {
              Serial.println("Public emqx mqtt broker connected");
          } else {
              Serial.print("failed with state ");
              Serial.print(client.state());
              delay(2000);
          }
      }
      // publish and subscribe
      client.publish(topic, "hello emqx");
      client.subscribe(topic);

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

  if(btnUnoPressed == HIGH || btnDosPressed == HIGH){
    if(btnUnoPressed == HIGH && btnDosPressed == HIGH){
      client.publish(topic,"Los dos");
      delay(1000);
    }else if(btnUnoPressed == HIGH){
      client.publish(topic,"Uno");
      delay(1000);
    }else if(btnDosPressed == HIGH){
      client.publish(topic,"Dos");
      delay(1000);
    }else{
      client.publish(topic,"No se");
      delay(1000);
    }
  }
  digitalWrite(btnUnoPressed,LOW);
  digitalWrite(btnDosPressed,LOW);
  //client.loop();
  //client.publish(topic,WiFi.macAddress().c_str());
  //delay(2000);
}