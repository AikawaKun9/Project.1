# Project.1
# 🎓 This is a Simple Student Information System

A desktop application built with **Java Swing** for managing student records. Features a clean yellowgreen-themed GUI with full CRUD operations, CSV-based data persistence, and separate management for colleges and programs.

---

## 📁 Project Structure

```
├── Studentinfosystem.java   # Main application — UI, CRUD, CSV logic
├── Student.java             # Student model class
├── Program.java             # Program model class
├── College.java             # College model class
├── students.csv             # Student records (auto-generated)
├── programs.csv             # Program list (auto-generated)
├── colleges.csv             # College list (auto-generated)
└── README.md
```

---

## ✅ Prerequisites

- **Java JDK 8 or higher**
- No external libraries required — uses only the Java standard library

---

## 🚀 How to Run

**1. Clone or download the repository**
```bash
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
```

**2. Compile all Java files**
```bash
javac *.java
```

**3. Run the application**
```bash
java Studentinfosystem
```

> ⚠️ Make sure all four `.java` files are in the same directory when compiling.

---

## 📋 CSV File Format

The application automatically creates the CSV files on first use. If you need to create them manually, follow these formats:

**`colleges.csv`**
```
code,name
CCS,College of Computer Studies
COE,College of Engineering
```

**`programs.csv`**
```
college,code,name
CCS,BSCS,Bachelor of Science in Computer Science
CCS,BSIT,Bachelor of Science in Information Technology
```

**`students.csv`**
```
id,firstname,lastname,program,college,year,gender
2024-0001,John,Dela Cruz,BSCS,CCS,1,M
```

> ⚠️ **Important:** The application validates all data on load. Rows with invalid formats will be automatically ignored.

---

## 🖥️ Features

### Student Management
| Action | Description |
|--------|-------------|
| **Add** | Fill in the form and click Add to create a new student record |
| **Update** | Select a student from the table and click Update to open an edit dialog |
| **Delete** | Select a student from the table and click Delete with confirmation |
| **Refresh** | Reloads all student data from the CSV file |

### Search
- **Live search** as you type — no need to press Enter
- Supports **multi-word search** across all columns — e.g. typing `Winchester Dela Vega` will find a student where `Winchester` is in First Name and `Dela Vega` is in Last Name
- Search is **case-insensitive**

### Sorting
- Click any **column header** to sort ascending or descending
- When two rows have equal values in the sorted column, they are **automatically sorted by ID** as a tiebreaker

### Program Manager
- Accessible via the **Manage Programs** button
- Add or delete academic programs
- Each program requires a **College** (selected from dropdown), **Program Code** (letters only, 2–10 chars), and **Program Name** (letters only)

### College Manager
- Accessible via the **Manage Colleges** button
- Add or delete colleges
- Colleges **cannot be deleted** if programs are still assigned to them

---

## ✔️ Validation Rules

### Student Form
| Field | Rule |
|-------|------|
| ID | Format must be `YYYY-NNNN` (e.g. `2024-0001`), year between 2000–2099 |
| First Name | Letters and spaces only, minimum 2 characters |
| Last Name | Letters and spaces only, minimum 2 characters |
| Program | Must be selected from the dropdown |
| Year Level | Must be a number between `1` and `5` |
| Gender | Must be `M` or `F` |

### Program Manager
| Field | Rule |
|-------|------|
| College | Must be selected from the dropdown |
| Program Code | Letters only (e.g. `BSCS`), 2–10 characters |
| Program Name | Letters and spaces only, minimum 5 characters |

### College Manager
| Field | Rule |
|-------|------|
| College Code | Letters only (e.g. `CCS`), 2–10 characters |
| College Name | Letters and spaces only, minimum 5 characters |

> Names are **auto-capitalized** — typing `winchester dela vega` will be saved as `Winchester Dela Vega`.

---

## 🗂️ Setup Order

Since programs depend on colleges, follow this order when setting up fresh:

1. Open **Manage Colleges** → add your colleges first
2. Open **Manage Programs** → add programs (college dropdown will be populated)
3. Start adding students via the main form

---

## 👥 Authors

> Replace with your name(s) and student ID(s)

| Name | ID |
|------|----|
| Winchester T. dela Vega | 2024-0673 |

---

## 📄 License

This project was made for academic purposes.
