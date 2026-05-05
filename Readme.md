# 🚨 Community Disaster Response Network (CDRN)

A real-time platform that connects **citizens, volunteers, and authorities** to improve disaster response, coordination, and recovery.

---

## 🌍 Problem

During disasters (floods, earthquakes, fires), lack of coordination leads to:
- Delayed rescue operations  
- Poor communication  
- Inefficient resource allocation  

---

## 🎯 Solution

CDRN provides a **unified real-time system** for:
- Incident reporting  
- Volunteer coordination  
- Authority monitoring  
- Rescue tracking  

---

## ✨ Features

### 👤 Citizen
- Report incidents (flood, fire, injury, etc.)
- Send SOS with location
- Receive alerts and updates

### 🧑‍🚒 Volunteer
- View available tasks
- Accept and update task status
- Assist in rescue operations

### 🧠 Admin / Authority
- Monitor incidents in real-time
- Assign volunteers
- Manage resources and alerts

---

## ⚡ Real-Time Functionality
- Live updates using WebSockets  
- Instant incident broadcasting  
- Real-time status tracking  

---

## 🏗️ Tech Stack

**Frontend**
- React.js / Next.js

**Backend**
- Spring Boot
- Spring WebSocket (STOMP)

**Database**
- MySQL / PostgreSQL

**Tools & APIs**
- Google Maps / Mapbox  
- Twilio (for notifications)

---

## 🧩 System Architecture

The Community Disaster Response Network (CDRN) follows a **client-server architecture** with real-time communication support.

The **frontend (React/Next.js)** acts as the user interface for citizens, volunteers, and administrators. Users can report incidents, view updates, and manage tasks through a simple and interactive dashboard.

The **backend is built using Spring Boot**, which handles all business logic, API requests, authentication, and data processing. It exposes REST APIs for standard operations and uses **WebSockets (STOMP protocol)** for real-time communication.

When a citizen reports an incident, the request is sent to the backend via REST API. The backend stores the data in the database and simultaneously broadcasts the update to all connected clients (admin and volunteers) using WebSockets. This ensures that all users receive updates instantly.

The **database (MySQL/PostgreSQL)** is used to store user data, incident reports, task assignments, and status updates. Optionally, **Redis** can be used to cache frequently accessed data and improve performance.

For visualization, **Map APIs (Google Maps / Mapbox)** are integrated into the frontend to display incident locations, safe routes, and affected areas.

Overall, the architecture ensures:
- Real-time communication  
- Scalability  
- Separation of concerns  
- Efficient disaster response coordination  

