# 6 Lab
  1. Для 6 лабораторної роботи нам потрібно створити проект на Google Cloud Platform
  2. Створити Віртуальну машину для цієї машини прописати Firewall elasic на порті tcp:9200 та kibana tcp:5601
  3. Підключившись до машини через SSH прописуєм наступні команди
  встанновлення джави
```sh
sudo apt-get install default-jre
```
  встановлення elastic і запуск
```sh
sudo apt update
sudo apt install apt-transport-https
wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -
sudo sh -c 'echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" > /etc/apt/sources.list.d/elastic-7.x.list'
sudo apt update
sudo apt install elasticsearch
sudo service elasticsearch status
sudo systemctl enable elasticsearch.service
sudo systemctl start elasticsearch.service
```
змінюємо нетворк хост на 0.0.0.0
```sh
sudo nano /etc/elasticsearch/elasticsearch.yml
#після цього рестартуємо еластік
sudo service elasticsearch restart
```
після цього встановлюєм логстеш та кібану
```sh
sudo apt-get install apt-transport-https
echo "deb https://artifacts.elastic.co/packages/5.x/apt stable main" | sudo tee -a /etc/apt/sources.list.d/elastic-5.x.list
sudo apt-get update
sudo apt-get install logstash
sudo service logstash start
echo "deb http://packages.elastic.co/kibana/7.0/debian stable main" | sudo tee -a /etc/apt/sources.list.d/kibana-7.0.x.list
sudo wget --directory-prefix=/opt/ https://artifacts.elastic.co/downloads/kibana/kibana-7.6.1-amd64.deb
sudo dpkg -i /opt/kibana*.deb
sudo apt-get update
sudo apt-get install kibana
```
редагуємо файл, пропусуємо server.host "0.0.0.0" а також розкоментовуємо порт 5601 для кібани
```sh
sudo nano /etc/kibana/kibana.yml
```
запускажмо та перевіряємо готовність кібани
```sh
sudo service kibana start
sudo service kibana status
```
4. Після усіх виконаних команд потрібно перейти по екстернал айпі http://104.198.172.153:5601 та http://104.198.172.153:9200 наприклад
5. Тепер потрібно створити Logic app у Azure та створити connect у Logic app designer та заранити
6. Після цього у індексах можна буде побачити дані






