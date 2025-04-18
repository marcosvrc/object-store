# Object Store Demo

Este projeto é uma demonstração de implementação de um serviço de armazenamento de objetos (Object Store) utilizando Spring Boot e MinIO. O sistema permite o upload, download e gerenciamento de arquivos através de uma API REST, com metadados armazenados em banco de dados H2 e os arquivos físicos no MinIO.

## 🚀 Tecnologias Utilizadas

### Backend
- Java 21
- Spring Boot 3.4.2
- Spring Data JPA
- Spring Web
- Lombok
- H2 Database

### Armazenamento de Objetos
- MinIO 8.5.16 (Compatível com Amazon S3)

## 📋 Pré-requisitos

Para executar este projeto, você precisará ter instalado em sua máquina:

- Java Development Kit (JDK) 21
- Maven 3.6+
- Docker (para executar o MinIO)
- Docker Compose (opcional, mas recomendado)

## 🔧 Configuração do Ambiente

### 1. Configurando o MinIO

O MinIO pode ser iniciado usando Docker com o seguinte comando:

```bash
docker run -p 9000:9000 -p 9001:9001 \
  --name minio \
  -e "MINIO_ROOT_USER=minioadmin" \
  -e "MINIO_ROOT_PASSWORD=minioadmin" \
  -v minio_data:/data \
  minio/minio server /data --console-address ":9001"
```

Ou, se preferir usar Docker Compose, crie um arquivo `docker-compose.yml`:

```yaml
version: '3.7'
services:
  minio:
    image: minio/minio
    ports:
      - "9000:9000"
      - "9001:9001"
    environment:
      MINIO_ROOT_USER: minioadmin
      MINIO_ROOT_PASSWORD: minioadmin
    volumes:
      - minio_data:/data
    command: server /data --console-address ":9001"
volumes:
  minio_data:
```

E execute:
```bash
docker-compose up -d
```

### 2. Configuração da Aplicação

A aplicação utiliza o arquivo `application.properties` para configuração. Certifique-se de que as seguintes propriedades estejam configuradas:

```properties
# Configurações do Servidor
server.port=8080

# Configurações do H2
spring.datasource.url=jdbc:h2:mem:objectstoredb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Configurações do MinIO
minio.endpoint=http://localhost:9000
minio.accessKey=minioadmin
minio.secretKey=minioadmin
minio.bucket=object-store
```

## 🚀 Executando a Aplicação

1. Clone o repositório:
```bash
git clone https://github.com/marcosvrc/object-store.git
cd object-store
```

2. Compile o projeto:
```bash
./mvnw clean package
```

3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: http://localhost:8080

## 📚 Endpoints da API

### Upload de Arquivo
```http
POST /api/objects
Content-Type: multipart/form-data

file: [arquivo]
```

### Download de Arquivo
```http
GET /api/objects/{objectId}
```

### Listar Objetos
```http
GET /api/objects
```

### Deletar Objeto
```http
DELETE /api/objects/{objectId}
```

## 🔍 Acessando Recursos Adicionais

### Console H2
O console do H2 está disponível em: http://localhost:8080/h2-console

Credenciais padrão:
- JDBC URL: jdbc:h2:mem:objectstoredb
- Username: sa
- Password: [vazio]

### Console MinIO
O console de administração do MinIO está disponível em: http://localhost:9001

Credenciais padrão:
- Username: minioadmin
- Password: minioadmin

## 🏗️ Estrutura do Projeto

```
object-store/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/
│   │   │       └── com/
│   │   │           └── objectstore/
│   │   │               ├── config/
│   │   │               ├── controller/
│   │   │               ├── model/
│   │   │               ├── repository/
│   │   │               └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## 🛠️ Tecnologias Detalhadas

### Spring Boot (3.4.2)
- Framework principal para desenvolvimento da aplicação
- Fornece configuração automática e gerenciamento de dependências
- Inclui servidor web embutido (Tomcat)

### MinIO (8.5.16)
- Sistema de armazenamento de objetos compatível com Amazon S3
- Oferece alta disponibilidade e escalabilidade
- Ideal para armazenamento de arquivos não estruturados

### H2 Database
- Banco de dados em memória para desenvolvimento
- Console web para gerenciamento de dados
- Não requer instalação adicional

### Lombok
- Reduz boilerplate code através de anotações
- Melhora a legibilidade do código
- Gera automaticamente getters, setters, construtores, etc.

## 📦 Compilação

O projeto usa Maven para gerenciamento de dependências e build. Os principais comandos são:

```bash
# Compilar e executar testes
./mvnw clean test

# Compilar e gerar JAR
./mvnw clean package

# Executar a aplicação
./mvnw spring-boot:run
```

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## ✒️ Autor

* **Marcos Vinicius** - [marcosvrc](https://github.com/marcosvrc)