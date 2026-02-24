import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class Studentinfosystem extends JFrame {

    private List<Student> students = new ArrayList<>();

//CSV files
    private static final String STUDENT_FILE = "students.csv";
    private static final String PROGRAM_FILE = "programs.csv";
    private static final String COLLEGE_FILE = "colleges.csv";
    private static final String CSV_HEADER   = "id,firstname,lastname,program,college,year,gender";

//Designs
    private static final Color BG          = new Color(0xF4F9E9);
    private static final Color ACCENT      = new Color(0x8DB600);
    private static final Color ACCENT_DARK = new Color(0x6A8C00);
    private static final Color SURFACE     = Color.WHITE;
    private static final Color TEXT_DARK   = new Color(0x1E1E1E);
    private static final Color TEXT_MUTED  = new Color(0x7A7A7A);
    private static final Color BORDER_COL  = new Color(0xDDE8B0);
    private static final Color ROW_ALT     = new Color(0xF8FCF0);

    private static final Font FONT_LABEL   = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_FIELD   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BTN     = new Font("Segoe UI", Font.BOLD,  12);
    private static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD,  18);
    private static final Font FONT_TABLE   = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_HEADER  = new Font("Segoe UI", Font.BOLD,  12);

//Text input
    private JTextField id, name, surname, yearlvl, gender, searchField;
    private JComboBox<String> program;

//Table
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;
    private DefaultTableModel model;

//Title
    private JPanel TitleBar() {
        JPanel bar = new JPanel(new BorderLayout());
            bar.setBackground(ACCENT);
            bar.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));

        JLabel title = new JLabel("Student Information System");
            title.setFont(FONT_TITLE);
            title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Manage your student records");
            sub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            sub.setForeground(new Color(255, 255, 255, 180));

        JPanel text = new JPanel(new GridLayout(2, 1));
            text.setOpaque(false);
            text.add(title);
            text.add(sub);
            bar.add(text, BorderLayout.WEST);
        return bar;
    }

//Form Card
    private JPanel FormCard() {
        JPanel card = new JPanel(new BorderLayout());
            card.setBackground(SURFACE);
            card.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COL, 1, true),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)
            ));

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setBackground(SURFACE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

            id      = styledField();
            name    = styledField();
            surname = styledField();
            yearlvl = styledField();
            gender  = styledField();
            program = styledCombo();
        loadProgramChoices();

        //Row 1: labels
        gbc.gridy = 0;
        gbc.gridx = 0; gbc.weightx = 0.8; grid.add(styledLabel("ID (YYYY-NNNN)"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; grid.add(styledLabel("First Name"), gbc);
        gbc.gridx = 2; gbc.weightx = 1.0; grid.add(styledLabel("Last Name"), gbc);
        gbc.gridx = 3; gbc.weightx = 2.0; grid.add(styledLabel("Program"), gbc);
        gbc.gridx = 4; gbc.weightx = 0.5; grid.add(styledLabel("Year Level"), gbc);
        gbc.gridx = 5; gbc.weightx = 0.6; grid.add(styledLabel("Gender (M/F)"), gbc);

        //Row 2: fields
        gbc.gridy = 1;
        gbc.gridx = 0; gbc.weightx = 0.8; grid.add(id, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; grid.add(name, gbc);
        gbc.gridx = 2; gbc.weightx = 1.0; grid.add(surname, gbc);
        gbc.gridx = 3; gbc.weightx = 2.0; grid.add(program, gbc);
        gbc.gridx = 4; gbc.weightx = 0.5; grid.add(yearlvl, gbc);
        gbc.gridx = 5; gbc.weightx = 0.6; grid.add(gender, gbc);

        card.add(grid, BorderLayout.CENTER);
        return card;
    }

//TABLE CARD
    private JPanel TableCard() {
        model = new DefaultTableModel(
            new String[]{"ID", "First Name", "Last Name", "Program", "College", "Year", "Gender"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        table.setFont(FONT_TABLE);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(0xD4EE85));
        table.setSelectionForeground(TEXT_DARK);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(SURFACE);
        table.setForeground(TEXT_DARK);

        //Alternate row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setFont(FONT_TABLE);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                if (!sel) {
                    setBackground(row % 2 == 0 ? SURFACE : ROW_ALT);
                    setForeground(TEXT_DARK);
                }
                return this;
            }
        });

        //Style header
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_HEADER);
        header.setBackground(ACCENT);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 34));
        header.setBorder(BorderFactory.createEmptyBorder());
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(SwingConstants.LEFT);

        //Selecting row fills form
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1)
                populateFormFromRow(table.getSelectedRow());
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(BORDER_COL, 1, true));
        scroll.getViewport().setBackground(SURFACE);

        searchField = new JTextField();
        searchField.setFont(FONT_FIELD);
        searchField.setForeground(TEXT_MUTED);
        searchField.setBackground(SURFACE);
        searchField.setText("Search students...");
        searchField.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COL, 1, true),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("Search students...")) {
                    searchField.setText("");
                    searchField.setForeground(TEXT_DARK);
                }
            }
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Search students...");
                    searchField.setForeground(TEXT_MUTED);
                    loadStudents();
                }
            }
        });

        //Live filter as user types
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { filterTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { filterTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
        });

        JLabel searchIcon = styledLabel("Search:");
        searchIcon.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel searchRow = new JPanel(new BorderLayout(8, 0));
        searchRow.setBackground(BG);
        searchRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        searchRow.add(searchIcon,  BorderLayout.WEST);
        searchRow.add(searchField, BorderLayout.CENTER);

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG);
        card.add(searchRow, BorderLayout.NORTH);
        card.add(scroll,    BorderLayout.CENTER);
        return card;
    }

    private void filterTable() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty() || keyword.equalsIgnoreCase("Search students...")) {
            sorter.setRowFilter(null);
            return;
        }

        String[] words = keyword.trim().split("\\s+");

        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        for (String word : words) {
            try {
                filters.add(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(word)));
            } catch (java.util.regex.PatternSyntaxException ex) {
            }
        }

        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
        }
    }

//Center (Form and Table)
    private JPanel buildCenter() {
        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.setBackground(BG);
        center.setBorder(BorderFactory.createEmptyBorder(16, 20, 8, 20));
        center.add(FormCard(),  BorderLayout.NORTH);
        center.add(TableCard(), BorderLayout.CENTER);
        return center;
    }

//Button Bar
    private JPanel ButtonBar() {
        JButton addBtn      = accentButton("Add");
        JButton updateBtn   = accentButton("Update");
        JButton deleteBtn   = ghostButton("Delete");
        JButton refreshBtn  = ghostButton("Refresh");
        JButton programBtn  = ghostButton("Manage Programs");
        JButton collegeBtn  = ghostButton("Manage Colleges");

        addBtn.addActionListener(e -> addStudent());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        refreshBtn.addActionListener(e -> loadStudents());
        programBtn.addActionListener(e -> openProgramManager());
        collegeBtn.addActionListener(e -> openCollegeManager());

        JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 12));
        bar.setBackground(BG);
        bar.setBorder(new MatteBorder(1, 0, 0, 0, BORDER_COL));
        bar.add(addBtn);
        bar.add(updateBtn);
        bar.add(deleteBtn);
        bar.add(Box.createHorizontalStrut(12));
        bar.add(refreshBtn);
        bar.add(Box.createHorizontalStrut(12));
        bar.add(programBtn);
        bar.add(collegeBtn);
        return bar;
    }

//Constructor
    public Studentinfosystem() {
        setTitle("Student Information System");
        setSize(1100, 620);
        setMinimumSize(new Dimension(900, 550));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 0));

        add(TitleBar(),    BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(ButtonBar(),   BorderLayout.SOUTH);

        loadStudents();
    }

//Styled Components
    private JTextField styledField() {
        JTextField f = new JTextField();
        f.setFont(FONT_FIELD);
        f.setForeground(TEXT_DARK);
        f.setBackground(SURFACE);
        f.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COL, 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return f;
    }

    private JComboBox<String> styledCombo() {
        JComboBox<String> cb = new JComboBox<>();
        cb.setFont(FONT_FIELD);
        cb.setBackground(SURFACE);
        cb.setForeground(TEXT_DARK);
        cb.setBorder(new LineBorder(BORDER_COL, 1, true));
        return cb;
    }

    private JLabel styledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_LABEL);
        l.setForeground(TEXT_MUTED);
        return l;
    }

    private JButton accentButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? ACCENT_DARK : getModel().isRollover() ? ACCENT_DARK : ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(90, 34));
        return btn;
    }

    private JButton ghostButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(0xEEF5CC) : SURFACE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(ACCENT);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(ACCENT_DARK);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 34));
        return btn;
    }

//Form field getters
    private String getIdField()      { return id.getText().trim(); }
    private String getNameField()    { return name.getText().trim(); }
    private String getSurnameField() { return surname.getText().trim(); }
    private String getYearField()    { return yearlvl.getText().trim(); }
    private String getGenderField()  { return gender.getText().trim(); }
    private String getProgramField() { Object sel = program.getSelectedItem(); return sel != null ? sel.toString() : ""; }

//Form field setters
    private void setIdField(String v)      { id.setText(v); }
    private void setNameField(String v)    { name.setText(v); }
    private void setSurnameField(String v) { surname.setText(v); }
    private void setYearField(String v)    { yearlvl.setText(v); }
    private void setGenderField(String v)  { gender.setText(v); }
    private void setProgramField(String v) {
        if (v == null) return;
        for (int i = 0; i < program.getItemCount(); i++) {
            String item = program.getItemAt(i);
            String itemCode = item.split(" - ")[0];
            if (itemCode.equals(v)) {
                program.setSelectedIndex(i);
                return;
            }
        }
    }

    private void clearFields() {
        setIdField(""); setNameField(""); setSurnameField("");
        setYearField(""); setGenderField("");
        if (program.getItemCount() > 0) program.setSelectedIndex(0);
    }

    private void populateFormFromRow(int viewRow) {
        int row = table.convertRowIndexToModel(viewRow);
        setIdField     ((String) model.getValueAt(row, 0));
        setNameField   ((String) model.getValueAt(row, 1));
        setSurnameField((String) model.getValueAt(row, 2));
        setProgramField((String) model.getValueAt(row, 3));
        // column 4 = College (auto-resolved, not a form field)
        setYearField   ((String) model.getValueAt(row, 5));
        setGenderField ((String) model.getValueAt(row, 6));
    }

    private String getCollegeForProgram(String code) {
        return readPrograms().stream()
            .filter(p -> p.getCode().equals(code))
            .map(Program::getCollege)
            .findFirst()
            .orElse("");
    }

    private String capitalizeName(String input) {
        if (input == null || input.trim().isEmpty()) return input;
        String[] words = input.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase());
                result.append(" ");
            }
        }
        return result.toString().trim();
    }

    private Student buildStudentFromForm() {
        String programCode = getProgramField().split(" - ")[0];
        Student s = new Student();
        s.setId(getIdField());
        s.setFirstName(capitalizeName(getNameField()));
        s.setLastName(capitalizeName(getSurnameField()));
        s.setProgram(programCode);
        s.setCollege(getCollegeForProgram(programCode));
        s.setYearLevel(getYearField());
        s.setGender(getGenderField());
        return s;
    }

//Validation
    private String validateStudentForm() {
        if (getIdField().isEmpty() || getNameField().isEmpty() || getSurnameField().isEmpty()
                || getYearField().isEmpty() || getGenderField().isEmpty() || getProgramField().isEmpty())
            return "All fields must be filled in!";
        if (!getIdField().matches("\\d{4}-\\d{4}"))
            return "Invalid ID format! Must be YYYY-NNNN (e.g. 2024-0001)";
        int yearPart = Integer.parseInt(getIdField().substring(0, 4));
        if (yearPart < 2000 || yearPart > 2099)
            return "Year in ID must be between 2000 and 2099!";
        if (!getNameField().matches("[a-zA-Z ]+") || !getSurnameField().matches("[a-zA-Z ]+"))
            return "Name fields must contain letters only!";
        if (getNameField().trim().isEmpty() || getSurnameField().trim().isEmpty())
            return "Name fields cannot be blank!";
        if (getNameField().trim().length() < 2 || getSurnameField().trim().length() < 2)
            return "Names must be at least 2 characters long!";
        if (!getYearField().matches("[1-5]"))
            return "Year level must be between 1 and 5!";
        if (!getGenderField().equalsIgnoreCase("M") && !getGenderField().equalsIgnoreCase("F"))
            return "Gender must be M or F!";
        return null;
    }

    private boolean isValidStudentRow(String[] data) {
        if (data.length < 7) return false;
        String id        = data[0].trim();
        String firstName = data[1].trim();
        String lastName  = data[2].trim();
        String prog      = data[3].trim();
        String college   = data[4].trim();
        String year      = data[5].trim();
        String gender    = data[6].trim();
        if (!id.matches("\\d{4}-\\d{4}"))             return false;
        int yearPart = Integer.parseInt(id.substring(0, 4));
        if (yearPart < 2000 || yearPart > 2099)        return false;
        if (!firstName.matches("[a-zA-Z ]+"))           return false;
        if (!lastName.matches("[a-zA-Z ]+"))            return false;
        if (firstName.trim().length() < 2)              return false;
        if (lastName.trim().length() < 2)               return false;
        if (!prog.matches("[A-Z0-9]+"))                 return false;
        if (college.isEmpty())                          return false;
        if (!year.matches("[1-5]"))                     return false;
        if (!gender.equalsIgnoreCase("M")
         && !gender.equalsIgnoreCase("F"))              return false;
        return true;
    }

//CSV: Students
    private List<Student> readStudents() {
        List<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(STUDENT_FILE))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (!isValidStudentRow(data)) continue;
                Student s = Student.fromArray(data);
                if (s != null) list.add(s);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    private void writeStudents(List<Student> students) {
        try (PrintWriter pw = new PrintWriter(STUDENT_FILE)) {
            pw.println(CSV_HEADER);
            for (Student s : students) pw.println(s.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving students: " + e.getMessage());
        }
    }

    private void loadStudents() {
        model.setRowCount(0);
        students = readStudents();
        students.sort(java.util.Comparator.comparing(Student::getLastName));
        for (Student s : students) model.addRow(s.toArray());
        clearFields();
        if (searchField != null) {
            searchField.setText("Search students...");
            searchField.setForeground(TEXT_MUTED);
        }
    }

//CSV: Programs
    private List<Program> readPrograms() {
        List<Program> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PROGRAM_FILE))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                Program p = Program.fromArray(line.split(","));
                if (p != null) list.add(p);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    private void writePrograms(List<Program> programs) {
        try (PrintWriter pw = new PrintWriter(PROGRAM_FILE)) {
            pw.println("college,code,name");
            for (Program p : programs) pw.println(p.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving programs: " + e.getMessage());
        }
    }

    private void loadProgramChoices() {
        program.removeAllItems();
        for (Program p : readPrograms())
            program.addItem(p.getCode() + " - " + p.getName());
        String longest = "";
        for (int i = 0; i < program.getItemCount(); i++) {
            String item = program.getItemAt(i);
            if (item.length() > longest.length()) longest = item;
        }
        program.setPrototypeDisplayValue(longest);
    }

//CSV: Colleges
    private List<College> readColleges() {
        List<College> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(COLLEGE_FILE))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                College c = College.fromArray(line.split(",", 2)); // limit 2 so name can have commas
                if (c != null) list.add(c);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    private void writeColleges(List<College> colleges) {
        try (PrintWriter pw = new PrintWriter(COLLEGE_FILE)) {
            pw.println("code,name");
            for (College c : colleges) pw.println(c.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving colleges: " + e.getMessage());
        }
    }

//CRUD: Students
    private void addStudent() {
        String error = validateStudentForm();
        if (error != null) { JOptionPane.showMessageDialog(this, error); return; }

        Student s = buildStudentFromForm();
        students = readStudents();
        if (students.stream().anyMatch(x -> x.getId().equals(s.getId()))) {
            JOptionPane.showMessageDialog(this, "A student with this ID already exists!");
            return;
        }
        students.add(s);
        writeStudents(students);
        loadStudents();
        JOptionPane.showMessageDialog(this, "Student added successfully!");
    }

    private void updateStudent() {
        if (getIdField().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table first.");
            return;
        }

        // Find the selected student
        String selectedId = getIdField();
        students = readStudents();
        Student target = students.stream()
            .filter(s -> s.getId().equals(selectedId))
            .findFirst().orElse(null);
        if (target == null) {
            JOptionPane.showMessageDialog(this, "Student not found."); return;
        }

        // ---- Build the update dialog ----
        JDialog dialog = new JDialog(this, "Edit Student — " + target.getId(), true);
        dialog.setSize(420, 380);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(BG);

        // Header bar
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ACCENT);
        header.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        JLabel headerLbl = new JLabel("Editing: " + target.getFirstName() + " " + target.getLastName()
                + "  ·  ID: " + target.getId());
        headerLbl.setFont(FONT_BTN);
        headerLbl.setForeground(Color.WHITE);
        header.add(headerLbl, BorderLayout.WEST);

        // Form fields pre-filled with current data
        JTextField dFirst   = styledField();
        JTextField dLast    = styledField();
        JTextField dYear    = styledField();
        JTextField dGender  = styledField();
        JComboBox<String> dProgram = styledCombo();

        // Populate program combo
        for (Program p : readPrograms())
            dProgram.addItem(p.getCode() + " - " + p.getName());

        // Pre-fill values
        dFirst.setText(target.getFirstName());
        dLast.setText(target.getLastName());
        dYear.setText(target.getYearLevel());
        dGender.setText(target.getGender());

        // Select current program in combo
        for (int i = 0; i < dProgram.getItemCount(); i++) {
            if (dProgram.getItemAt(i).startsWith(target.getProgram())) {
                dProgram.setSelectedIndex(i); break;
            }
        }

        // Form grid — vertical: label on left, field on right, one row per field
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setBackground(SURFACE);
        grid.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(6, 6, 6, 6);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.anchor  = GridBagConstraints.WEST;

        // Label column — fixed width
        gbc.gridx = 0; gbc.weightx = 0;

        gbc.gridy = 0; grid.add(styledLabel("First Name"),    gbc);
        gbc.gridy = 1; grid.add(styledLabel("Last Name"),     gbc);
        gbc.gridy = 2; grid.add(styledLabel("Program"),       gbc);
        gbc.gridy = 3; grid.add(styledLabel("Year Level"),    gbc);
        gbc.gridy = 4; grid.add(styledLabel("Gender (M/F)"),  gbc);

        // Field column — takes remaining width
        gbc.gridx = 1; gbc.weightx = 1.0;

        gbc.gridy = 0; grid.add(dFirst,   gbc);
        gbc.gridy = 1; grid.add(dLast,    gbc);
        gbc.gridy = 2; grid.add(dProgram, gbc);
        gbc.gridy = 3; grid.add(dYear,    gbc);
        gbc.gridy = 4; grid.add(dGender,  gbc);

        // Buttons
        JButton saveBtn   = accentButton("Save");
        JButton cancelBtn = ghostButton("Cancel");
        JPanel btnPanel   = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        btnPanel.setBackground(BG);
        btnPanel.setBorder(new MatteBorder(1, 0, 0, 0, BORDER_COL));
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);

        saveBtn.addActionListener(e -> {
            String firstName = dFirst.getText().trim();
            String lastName  = dLast.getText().trim();
            String year      = dYear.getText().trim();
            String gend      = dGender.getText().trim();
            Object progSel   = dProgram.getSelectedItem();
            String progStr   = progSel != null ? progSel.toString() : "";

            // Validate
            if (firstName.isEmpty() || lastName.isEmpty() || year.isEmpty()
                    || gend.isEmpty() || progStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields must be filled in!"); return;
            }
            if (!firstName.matches("[a-zA-Z ]+") || firstName.trim().length() < 2) {
                JOptionPane.showMessageDialog(dialog, "First name must be letters only, at least 2 characters!"); return;
            }
            if (!lastName.matches("[a-zA-Z ]+") || lastName.trim().length() < 2) {
                JOptionPane.showMessageDialog(dialog, "Last name must be letters only, at least 2 characters!"); return;
            }
            if (!year.matches("[1-5]")) {
                JOptionPane.showMessageDialog(dialog, "Year level must be between 1 and 5!"); return;
            }
            if (!gend.equalsIgnoreCase("M") && !gend.equalsIgnoreCase("F")) {
                JOptionPane.showMessageDialog(dialog, "Gender must be M or F!"); return;
            }

            // Apply changes
            String programCode = progStr.split(" - ")[0];
            target.setFirstName(capitalizeName(firstName));
            target.setLastName(capitalizeName(lastName));
            target.setProgram(programCode);
            target.setCollege(getCollegeForProgram(programCode));
            target.setYearLevel(year);
            target.setGender(gend);

            writeStudents(students);
            loadStudents();
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        dialog.add(header,   BorderLayout.NORTH);
        dialog.add(grid,     BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void deleteStudent() {
        if (getIdField().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student from the table first.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete student " + getIdField() + "? This cannot be undone.",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        students = readStudents();
        if (!students.removeIf(s -> s.getId().equals(getIdField()))) {
            JOptionPane.showMessageDialog(this, "Student ID not found."); return;
        }
        writeStudents(students);
        loadStudents();
        JOptionPane.showMessageDialog(this, "Student deleted successfully!");
    }

//Manage Colleges Dialog
    private void openCollegeManager() {
        JDialog dialog = new JDialog(this, "College Manager", true);
        dialog.setSize(480, 360);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(BG);

        JTextField codeField = styledField();
        JTextField nameField = styledField();

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 8));
        form.setBackground(SURFACE);
        form.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, BORDER_COL),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));
        form.add(styledLabel("College Code"));
        form.add(styledLabel("College Name"));
        form.add(codeField);
        form.add(nameField);

        DefaultTableModel collModel = new DefaultTableModel(
            new String[]{"Code", "College Name"}, 0
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } };

        JTable collTable = new JTable(collModel);
        collTable.setFont(FONT_TABLE);
        collTable.setRowHeight(28);
        collTable.setShowGrid(false);
        collTable.setSelectionBackground(new Color(0xD4EE85));
        collTable.setSelectionForeground(TEXT_DARK);
        collTable.setBackground(SURFACE);
        collTable.setAutoCreateRowSorter(true);
        collTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        collTable.getColumnModel().getColumn(1).setPreferredWidth(360);

        JTableHeader ch = collTable.getTableHeader();
        ch.setFont(FONT_HEADER);
        ch.setBackground(ACCENT);
        ch.setForeground(Color.WHITE);
        ch.setPreferredSize(new Dimension(0, 32));

        // Load existing colleges
        for (College c : readColleges()) collModel.addRow(c.toArray());

        // Click row -> populate fields
        collTable.getSelectionModel().addListSelectionListener(e -> {
            int row = collTable.getSelectedRow();
            if (!e.getValueIsAdjusting() && row != -1) {
                int modelRow = collTable.convertRowIndexToModel(row);
                codeField.setText((String) collModel.getValueAt(modelRow, 0));
                nameField.setText((String) collModel.getValueAt(modelRow, 1));
            }
        });

        JScrollPane scroll = new JScrollPane(collTable);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(SURFACE);

        JButton addBtn    = accentButton("Add");
        JButton deleteBtn = ghostButton("Delete");
        JButton closeBtn  = ghostButton("Close");
        JPanel btnPanel   = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        btnPanel.setBackground(BG);
        btnPanel.setBorder(new MatteBorder(1, 0, 0, 0, BORDER_COL));
        btnPanel.add(addBtn); btnPanel.add(deleteBtn); btnPanel.add(closeBtn);

        addBtn.addActionListener(e -> {
            String code  = codeField.getText().trim().toUpperCase();
            String cname = nameField.getText().trim();
            if (code.isEmpty() || cname.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all fields."); return;
            }
            if (!code.matches("[A-Z]+")) {
                JOptionPane.showMessageDialog(dialog, "College Code must contain letters only! (e.g. CCS)"); return;
            }
            if (code.length() < 2 || code.length() > 10) {
                JOptionPane.showMessageDialog(dialog, "College Code must be 2–10 letters long!"); return;
            }
            if (!cname.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(dialog, "College Name must contain letters only!"); return;
            }
            if (cname.trim().length() < 5) {
                JOptionPane.showMessageDialog(dialog, "College Name is too short!"); return;
            }
            List<College> list = readColleges();
            if (list.stream().anyMatch(c -> c.getCode().equals(code))) {
                JOptionPane.showMessageDialog(dialog, "College code already exists!"); return;
            }
            College nc = new College(code, cname);
            list.add(nc);
            writeColleges(list);
            collModel.addRow(nc.toArray());
            codeField.setText(""); nameField.setText("");
        });

        deleteBtn.addActionListener(e -> {
            String code = codeField.getText().trim().toUpperCase();
            if (code.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Select a college to delete."); return;
            }
            // Warn if programs still use this college
            long inUse = readPrograms().stream()
                .filter(p -> p.getCollege().equalsIgnoreCase(code))
                .count();
            if (inUse > 0) {
                JOptionPane.showMessageDialog(dialog,
                    "Cannot delete — " + inUse + " program(s) still use this college.\nRemove those programs first.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(dialog,
                "Delete college " + code + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            List<College> list = readColleges();
            list.removeIf(c -> c.getCode().equals(code));
            writeColleges(list);
            collModel.setRowCount(0);
            for (College c : list) collModel.addRow(c.toArray());
            codeField.setText(""); nameField.setText("");
        });

        closeBtn.addActionListener(e -> dialog.dispose());

        dialog.add(form,     BorderLayout.NORTH);
        dialog.add(scroll,   BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    //Manage Programs Dialog
    private void openProgramManager() {
        JDialog dialog = new JDialog(this, "Program Manager", true);
        dialog.setSize(580, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(BG);

        // College is now a DROPDOWN from colleges.csv — not a free text field
        JComboBox<String> collegeCombo = styledCombo();
        for (College c : readColleges())
            collegeCombo.addItem(c.getCode() + " - " + c.getName());

        JTextField codeField = styledField();
        JTextField nameField = styledField();

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(SURFACE);
        form.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, BORDER_COL),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        gbc.gridx = 0; gbc.weightx = 3.0; form.add(styledLabel("College"), gbc);        // widest
        gbc.gridx = 1; gbc.weightx = 0.8; form.add(styledLabel("Program Code"), gbc);   // narrowest
        gbc.gridx = 2; gbc.weightx = 2.5; form.add(styledLabel("Program Name"), gbc);   // medium

        gbc.gridy = 1;
        gbc.gridx = 0; gbc.weightx = 3.0; form.add(collegeCombo, gbc);
        gbc.gridx = 1; gbc.weightx = 0.8; form.add(codeField, gbc);
        gbc.gridx = 2; gbc.weightx = 2.5; form.add(nameField, gbc);

        DefaultTableModel progModel = new DefaultTableModel(
            new String[]{"College", "Code", "Name"}, 0
        ) { @Override public boolean isCellEditable(int r, int c) { return false; } };

        JTable progTable = new JTable(progModel);
        progTable.setFont(FONT_TABLE);
        progTable.setRowHeight(28);
        progTable.setShowGrid(false);
        progTable.setSelectionBackground(new Color(0xD4EE85));
        progTable.setSelectionForeground(TEXT_DARK);
        progTable.setBackground(SURFACE);
        progTable.setAutoCreateRowSorter(true);
        progTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        progTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        progTable.getColumnModel().getColumn(2).setPreferredWidth(260);

        JTableHeader ph = progTable.getTableHeader();
            ph.setFont(FONT_HEADER);
            ph.setBackground(ACCENT);
            ph.setForeground(Color.WHITE);
            ph.setPreferredSize(new Dimension(0, 32));

        for (Program p : readPrograms()) progModel.addRow(p.toArray());

        // Click row -> populate fields
        progTable.getSelectionModel().addListSelectionListener(e -> {
            int row = progTable.getSelectedRow();
            if (!e.getValueIsAdjusting() && row != -1) {
                int modelRow = progTable.convertRowIndexToModel(row);
                String collegeCode = (String) progModel.getValueAt(modelRow, 0);
                // Select matching item in combo
                for (int i = 0; i < collegeCombo.getItemCount(); i++) {
                    if (collegeCombo.getItemAt(i).startsWith(collegeCode)) {
                        collegeCombo.setSelectedIndex(i); break;
                    }
                }
                codeField.setText((String) progModel.getValueAt(modelRow, 1));
                nameField.setText((String) progModel.getValueAt(modelRow, 2));
            }
        });

        JScrollPane scroll = new JScrollPane(progTable);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(SURFACE);

        JButton addBtn    = accentButton("Add");
        JButton deleteBtn = ghostButton("Delete");
        JButton closeBtn  = ghostButton("Close");
        JPanel btnPanel   = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        btnPanel.setBackground(BG);
        btnPanel.setBorder(new MatteBorder(1, 0, 0, 0, BORDER_COL));
        btnPanel.add(addBtn); btnPanel.add(deleteBtn); btnPanel.add(closeBtn);

        addBtn.addActionListener(e -> {
            Object sel = collegeCombo.getSelectedItem();
            if (sel == null) {
                JOptionPane.showMessageDialog(dialog, "Please select a college."); return;
            }
            String collegeCode = sel.toString().split(" - ")[0];
            String code  = codeField.getText().trim().toUpperCase();
            String pname = nameField.getText().trim();

            if (code.isEmpty() || pname.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all fields."); return;
            }
            if (!code.matches("[A-Z]+")) {
                JOptionPane.showMessageDialog(dialog, "Program Code must contain letters only! (e.g. BSCS)"); return;
            }
            if (code.length() < 2 || code.length() > 10) {
                JOptionPane.showMessageDialog(dialog, "Program Code must be 2–10 letters long!"); return;
            }
            if (!pname.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(dialog, "Program Name must contain letters only!"); return;
            }
            if (pname.trim().length() < 5) {
                JOptionPane.showMessageDialog(dialog, "Program Name is too short!"); return;
            }
            List<Program> list = readPrograms();
            if (list.stream().anyMatch(p -> p.getCode().equals(code))) {
                JOptionPane.showMessageDialog(dialog, "Program code already exists!"); return;
            }
            Program np = new Program(collegeCode, code, pname);
            list.add(np);
            writePrograms(list);
            progModel.addRow(np.toArray());
            codeField.setText(""); nameField.setText("");
            loadProgramChoices();
        });

        deleteBtn.addActionListener(e -> {
            String code = codeField.getText().trim().toUpperCase();
            if (code.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Select a program to delete."); return;
            }
            int confirm = JOptionPane.showConfirmDialog(dialog,
                "Delete program " + code + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            List<Program> list = readPrograms();
            list.removeIf(p -> p.getCode().equals(code));
            writePrograms(list);
            progModel.setRowCount(0);
            for (Program p : list) progModel.addRow(p.toArray());
            codeField.setText(""); nameField.setText("");
            loadProgramChoices();
        });

        closeBtn.addActionListener(e -> dialog.dispose());

        dialog.add(form,     BorderLayout.NORTH);
        dialog.add(scroll,   BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

//Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Studentinfosystem().setVisible(true));
    }
}