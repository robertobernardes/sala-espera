# Projeto Gestão de salas

#### **Sistema de Gerenciamento de Salas de Espera para Teleconsulta**


#### Tecnologias utilizadas

- Java » v21;
- Spring Boot » v3.5.7;
- Spring Data JPA » v3.5.7;
- Spring Data Validation » v3.5.7;
- Create an OCI image » v3.5.7;
- PrimeFaces Spring Boot Starter » v5.5.7;
- Spring Boot DevTools » v3.5.7;
- Hibernate Validator » v8.0.3.Final;
- Hibernate ORM Hibernate Community Dialects » 7.1.6.Final;
- Javax Servlet JSP API » v2.0;
- Javax Servlet JSTL » v1.2;
- Expression Language Implementation(org.glassfish.web - el-impl) » 2.2;
- SQLite » v3.50.3.0;
- Jakarta Server Faces (JSF);
- PrimeFaces;
- Maven » v3.9.11;
- Apache Tomcat » V11.0.4;
- Docker / Docker Compose;



#### Instalação e Configuração

###### **1 - Clone do projeto:**

```sh
    git@github.com:robertobernardes/sala-espera.git
```

###### **2 - Configuração da aplicação**
```sh
    cd sala-espera
```

###### **3 - Build da aplicação**
```sh
    ./docker-run.sh
```

###### **4 - Utilizando a aplicação**

| Descrição                           	|                          								Endpoint |
| :------------------------------------	|  ------------------------------------------------------------: |
| Dashboard - Gestão de Agendamentos 	|     			   http://localhost:8087/sala-espera/index.xhtml |
| Disponibilidade de Salas     | GET    |    http://localhost:8087/sala-espera/disponibilidadeSala.xhtml |
| Gerenciamento de Reserva das Salas   	|     		 http://localhost:8087/sala-espera/reservaSala.xhtml |
| Gerenciamento de Pacientes            |  				http://localhost:8087/sala-espera/paciente.xhtml |
| Gerenciamento de Usuários             |  				 http://localhost:8087/sala-espera/usuario.xhtml |
| Gerenciamento de Salas                |  					http://localhost:8087/sala-espera/sala.xhtml |
| Gerenciamento de Unidades de Saúde    |			http://localhost:8087/sala-espera/unidadeSaude.xhtml |