package latex.views;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import latex.controller.LatexEditorController;
import latex.model.Document;
import latex.model.VersionsManager;
import latex.model.strategies.VersionsStrategy;
import latex.model.strategies.VersionsStrategyFactory;
import javax.swing.JRadioButton;

public class LatexEditorView extends JFrame  {

	private static final long serialVersionUID = 1L;

	private static int OK = 0;
	private static String EMPTY		= "empty";
	private static String REPORT	= "report";
	private static String BOOK		= "book";
	private static String ARTICLE	= "article";
	private static String LETTER	= "letter";
	private static int NUMBER_OF_ARGUMENTS = 10;

	private static String CREATE = "Create";
	private static String ADD = "Add";
	private static String ROLLBACK = "Rollback";
	private static String EDIT = "Edit";
	private static String LOAD = "Load";
	private static String SAVE = "Save";
	private static String ENABLE = "Enable";
	private static String DISABLE = "Disable";
	private static String CHANGE_STRATEGY = "Change strategy";

	private static String VOLATILE = "Volatile";
	private static String STABLE = "Stable";

	// Controller
	/*The array of Objects is used to pass multiple arguments at latex.controller.commands's Command Interface's implemented classes*/
	private Object[] argumentsOfCommand = new Object[NUMBER_OF_ARGUMENTS];
	private LatexEditorController controller = new LatexEditorController();

	// Model
	private Document document;
	private VersionsStrategyFactory strategyFactory = new VersionsStrategyFactory();
	private VersionsStrategy versionsStrategy = strategyFactory.createStrategy(VOLATILE);	// VOLATILE by default
	private VersionsManager versionsManager = new VersionsManager(versionsStrategy);

	// Components
	private JPanel ctpMain;
	private JMenu mnSavingAs;
	private JMenuItem mntmExit;
	private JMenuItem mntmLoadFile;
	private JMenuItem mntmCreate;
	private JMenuItem mntmSave;
	private JRadioButtonMenuItem rdVolatile;
	private JRadioButtonMenuItem rdStable;
	private final ButtonGroup buttonGroupEnable_Disable = new ButtonGroup();
	private final ButtonGroup buttonGroupVolatile_Stable = new ButtonGroup();
	private JTextArea taDocument;
	private JComboBox<String> cbTemplate;
	private JButton btnCreateTemplate;
	private JButton btnLoad;
	private JButton btnSave;
	private JButton btnAddCommand;
	private JButton btnSubmitChanges;
	private JComboBox<String> cbCommands;

	private JFileChooser fileChooser = new JFileChooser();

	private String currentDocumentTemplate;
	private JMenu mnAutomaticVersionTracking;
	private JRadioButton rdEnabled;
	private JRadioButton rdDisabled;

	private String savingMethod = VOLATILE;				// VOLATILE by default
	private JButton btnRollback;
	private JLabel lblCommands;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LatexEditorView frame = new LatexEditorView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LatexEditorView() {

		setTitle("Latex editor");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LatexEditorView.class.getResource("/latex/recources/images/folder_icon-20x20.png")));

		initComponents();
		createEvents();
	}

	private void initComponents() {
		versionsManager.enable(); 						// Enabled by default

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmCreate = new JMenuItem("Create New");

		mnFile.add(mntmCreate);

		mntmSave = new JMenuItem("Save");

		mnFile.add(mntmSave);

		mntmLoadFile = new JMenuItem("Load file");

		mnFile.add(mntmLoadFile);

		mntmExit = new JMenuItem("Exit");

		mnFile.add(mntmExit);

		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		mnSavingAs = new JMenu("Saving as");

		mnOptions.add(mnSavingAs);

		rdVolatile = new JRadioButtonMenuItem("Volatile");
		rdVolatile.setSelected(true);
		buttonGroupVolatile_Stable.add(rdVolatile);
		mnSavingAs.add(rdVolatile);

		rdStable = new JRadioButtonMenuItem("Stable");
		buttonGroupVolatile_Stable.add(rdStable);
		mnSavingAs.add(rdStable);

		mnAutomaticVersionTracking = new JMenu("Automatic version tracking");
		mnOptions.add(mnAutomaticVersionTracking);

		rdEnabled = new JRadioButton("Enabled");
		rdEnabled.setSelected(true);

		buttonGroupEnable_Disable.add(rdEnabled);
		//rdEnabled.setSelected(true);
		mnAutomaticVersionTracking.add(rdEnabled);

		rdDisabled = new JRadioButton("Disabled");

		buttonGroupEnable_Disable.add(rdDisabled);
		mnAutomaticVersionTracking.add(rdDisabled);

		ctpMain = new JPanel();
		ctpMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(ctpMain);

		btnSave = new JButton("Save");
		btnSave.setVisible(false);
		btnLoad = new JButton("Load");
		btnLoad.setVisible(false);
		btnAddCommand = new JButton("Add");
		btnAddCommand.setVisible(false);
		btnSubmitChanges = new JButton("Submit changes");
		btnSubmitChanges.setVisible(false);
		btnCreateTemplate = new JButton("Create");

		JPanel pnlEditor = new JPanel();
		pnlEditor.setBorder(new TitledBorder(null, "Document:", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel pnlOptions = new JPanel();
		pnlOptions.setBorder(new TitledBorder(null, "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GroupLayout gl_ctpMain = new GroupLayout(ctpMain);
		gl_ctpMain.setHorizontalGroup(
			gl_ctpMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ctpMain.createSequentialGroup()
					.addGroup(gl_ctpMain.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_ctpMain.createSequentialGroup()
							.addGap(167)
							.addComponent(btnSubmitChanges)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSave)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnLoad))
						.addGroup(gl_ctpMain.createSequentialGroup()
							.addContainerGap()
							.addComponent(pnlEditor, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlOptions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_ctpMain.setVerticalGroup(
			gl_ctpMain.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_ctpMain.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_ctpMain.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlOptions, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
						.addComponent(pnlEditor, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_ctpMain.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnSubmitChanges)
						.addComponent(btnLoad))
					.addContainerGap())
		);

		JLabel lblTemplate = new JLabel("Template:");

		lblCommands = new JLabel("Commands:");
		lblCommands.setVisible(false);

		cbTemplate = new JComboBox<String>();
		cbTemplate.setVisible(false);
		cbTemplate.addItem("Empty");
		cbTemplate.addItem("Report");
		cbTemplate.addItem("Book");
		cbTemplate.addItem("Article");
		cbTemplate.addItem("Letter");
		cbTemplate.setSelectedIndex(1);

		cbCommands = new JComboBox<String>();
		cbCommands.setVisible(false);
		cbCommands.addItem("\\chapter{...}");
		cbCommands.addItem("\\section{}");
		cbCommands.addItem("\\subsection{}");
		cbCommands.addItem("\\subsubsection{}");
		cbCommands.addItem("\\begin{itemize}");
		cbCommands.addItem("\\item ...");
		cbCommands.addItem("\\end{itemize}");
		cbCommands.addItem("\\begin{enumerate}");
		cbCommands.addItem("\\end{enumerate}");
		cbCommands.addItem("\\begin{table}");
		cbCommands.addItem("\\caption{....}\\label{...}");
		cbCommands.addItem("\begin{tabular}{|c|c|c|}");
		cbCommands.addItem("\\hline");
		cbCommands.addItem("... &...&...\\\\");
		cbCommands.addItem("\\end{tabular}");
		cbCommands.addItem("\\end{table}");
		cbCommands.addItem("\\begin{figure}");
		cbCommands.addItem("\\includegraphics[width=...,height=...]{...}");
		cbCommands.addItem("\\caption{....}\\label{...}");
		cbCommands.addItem("\\end{figure}");

		btnRollback = new JButton("Rollback (Undo)");
		btnRollback.setVisible(false);

		GroupLayout gl_pnlOptions = new GroupLayout(pnlOptions);
		gl_pnlOptions.setHorizontalGroup(
			gl_pnlOptions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOptions.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlOptions.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTemplate)
						.addComponent(lblCommands))
					.addGap(21)
					.addGroup(gl_pnlOptions.createParallelGroup(Alignment.LEADING)
						.addComponent(btnRollback)
						.addGroup(gl_pnlOptions.createSequentialGroup()
							.addGroup(gl_pnlOptions.createParallelGroup(Alignment.LEADING, false)
								.addComponent(cbCommands, 0, 0, Short.MAX_VALUE)
								.addComponent(cbTemplate, 0, 115, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlOptions.createParallelGroup(Alignment.LEADING)
								.addComponent(btnAddCommand)
								.addComponent(btnCreateTemplate))))
					.addContainerGap(73, Short.MAX_VALUE))
		);
		gl_pnlOptions.setVerticalGroup(
			gl_pnlOptions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOptions.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTemplate)
						.addComponent(cbTemplate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCreateTemplate))
					.addGap(18)
					.addGroup(gl_pnlOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbCommands, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCommands)
						.addComponent(btnAddCommand))
					.addGap(18)
					.addComponent(btnRollback)
					.addContainerGap(140, Short.MAX_VALUE))
		);
		pnlOptions.setLayout(gl_pnlOptions);

		JScrollPane scrDocument = new JScrollPane();
		GroupLayout gl_pnlEditor = new GroupLayout(pnlEditor);
		gl_pnlEditor.setHorizontalGroup(
			gl_pnlEditor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlEditor.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrDocument, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlEditor.setVerticalGroup(
			gl_pnlEditor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlEditor.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrDocument, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
					.addContainerGap())
		);

		taDocument = new JTextArea();

		taDocument.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrDocument.setViewportView(taDocument);
		pnlEditor.setLayout(gl_pnlEditor);
		ctpMain.setLayout(gl_ctpMain);

	}

	private void createEvents() {
		//Buttons Listeners
		mntmCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createTemplatesAction();
			}
		});

		btnCreateTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createTemplatesAction();
			}
		});

		mntmLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFileAction();
			}
		});

		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadFileAction();
			}
		});

		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveFileAction();
				JOptionPane.showMessageDialog(null, "Latex document saved");
			}
		});

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFileAction();
				JOptionPane.showMessageDialog(null, "Latex document saved");
			}
		});

		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitProgramAction();
			}
		});

		btnAddCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addLatexCommandAction();
			}
		});

		btnSubmitChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editContentsAction();
			}
		});

		rdVolatile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				volatileSelectedAction();

				JOptionPane.showMessageDialog(null, "Saving option: Volatile");
			}
		});

		rdStable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stableSelectedAction();

				JOptionPane.showMessageDialog(null, "Saving option: Stable");
			}
		});

		rdEnabled.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableVersionManagementAction();

				JOptionPane.showMessageDialog(null, "Version tracking mechanism: Enabled.");

				buttonGroupVolatile_Stable.clearSelection();
				rdVolatile.setSelected(true);

				btnRollback.setVisible(true);
			}
		});

		rdDisabled.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disableVersionManagementAction();

				JOptionPane.showMessageDialog(null, "Version tracking mechanism: Disabled.");

				buttonGroupVolatile_Stable.clearSelection();
				rdVolatile.setSelected(false);

				btnRollback.setVisible(false);
			}
		});

		btnRollback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rollbackToPreviusVersionAction();
			}
		});

	}

	// Button's functions:
	private void rollbackToPreviusVersionAction(){
		argumentsOfCommand[0] = versionsStrategy;
		argumentsOfCommand[1] = versionsManager;
		argumentsOfCommand[2] = document;

		document = (Document) controller.enact(ROLLBACK, argumentsOfCommand);

		if(document == null){
			//System.out.println("No rollbacks");
			btnRollback.setVisible(false);

			taDocument.setText("");
			return;
		}

		taDocument.setText(document.getContents());
	}

	private void disableVersionManagementAction(){
		argumentsOfCommand[0] = versionsManager;

		controller.enact(DISABLE, argumentsOfCommand);
	}

	private void enableVersionManagementAction(){
		if(document == null){
			JOptionPane.showMessageDialog(null, "First, create a document");
			createTemplatesAction();
		}

		argumentsOfCommand[0] = versionsManager;
		argumentsOfCommand[1] = document;

		controller.enact(ENABLE, argumentsOfCommand);
	}

	private void volatileSelectedAction(){
		if(rdVolatile.isSelected() && versionsManager.isEnabled()){
			enableVersionManagementAction();
		}

		savingMethod = VOLATILE;

		argumentsOfCommand[0] = versionsStrategy;
		argumentsOfCommand[1] = versionsManager;
		argumentsOfCommand[2] = savingMethod;

		versionsManager = (VersionsManager) controller.enact(CHANGE_STRATEGY, argumentsOfCommand);
	}

	private void stableSelectedAction(){
		if(rdStable.isSelected() && versionsManager.isEnabled()){
			enableVersionManagementAction();
		}

		savingMethod = STABLE;

		argumentsOfCommand[0] = versionsStrategy;
		argumentsOfCommand[1] = versionsManager;
		argumentsOfCommand[2] = savingMethod;

		versionsManager = (VersionsManager) controller.enact(CHANGE_STRATEGY, argumentsOfCommand);
	}

	private void createTemplatesAction(){
		int choice = JOptionPane.showConfirmDialog(null, "Do you want to choose a default template?");

		if(choice == JOptionPane.YES_OPTION){
			//Pick a template
			cbTemplate.setVisible(true);
			JOptionPane.showInternalMessageDialog(ctpMain, cbTemplate, "Pick template", JOptionPane.QUESTION_MESSAGE);

			String chosenTemplate = (String) cbTemplate.getSelectedItem();
			if(JOptionPane.OK_OPTION != OK){
				return;
			}

			if (chosenTemplate.equals("Empty")){
				argumentsOfCommand[0] = new String(EMPTY);
			}
			else if (chosenTemplate.equals("Report")){
				argumentsOfCommand[0] = new String(REPORT);
			}
			else if (chosenTemplate.equals("Book")){
				argumentsOfCommand[0] = new String(BOOK);
			}
			else if (chosenTemplate.equals("Article")){
				argumentsOfCommand[0] = new String(ARTICLE);
			}
			else if (chosenTemplate.equals("Letter")){
				argumentsOfCommand[0] = new String(LETTER);
			}
			currentDocumentTemplate = (String) argumentsOfCommand[0];

			document = (Document) controller.enact(CREATE, argumentsOfCommand);

			if(versionsManager.isEnabled()){
				enableVersionManagementAction();
			}

			taDocument.setText(document.getContents());
		}
		else if(choice == JOptionPane.NO_OPTION){
			//Loading an empty Latex document
			argumentsOfCommand[0] = new String(EMPTY);
			currentDocumentTemplate = new String(EMPTY);

			document = (Document) controller.enact(CREATE, argumentsOfCommand);

			if(versionsManager.isEnabled()){
				enableVersionManagementAction();
			}
		}
		else if(choice == JOptionPane.CLOSED_OPTION){
			return;
		}

		taDocument.setText(document.getContents());

		createTemplatesVisuals();
}

	private void loadFileAction(){
		if(document == null){
			//Create an EMPTY document
			argumentsOfCommand[0] = new String(EMPTY);
			document = (Document) controller.enact("Create", argumentsOfCommand);
		}

		fileChooser.setDialogTitle("Specify a file to open");
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			java.io.File file = fileChooser.getSelectedFile();

			argumentsOfCommand[0] = file;
			String contents = (String) controller.enact(LOAD, argumentsOfCommand);

			document.setContents(contents);
		}
		else{
			JOptionPane.showMessageDialog(null, "No file was selected");
		}

		taDocument.setText(document.getContents());
	}

	private void saveFileAction(){
		if(document == null){
			JOptionPane.showMessageDialog(null, "There is no file to save");
			createTemplatesAction();
		}

		fileChooser.setDialogTitle("Specify a file to save");
		int userSelection = fileChooser.showSaveDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser.getSelectedFile();

			try {
				document = editContentsAction();
				argumentsOfCommand[0] = document;
				argumentsOfCommand[1] = file;
				controller.enact(SAVE, argumentsOfCommand);
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
			}
		}
	}

	private void exitProgramAction(){
		int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to Exit?");
		if (choice == JOptionPane.YES_NO_OPTION ){
			//Just exits
			System.exit(0);
		}
	}

	private void addLatexCommandAction(){
		cbCommands.setVisible(true);
		JOptionPane.showInternalMessageDialog(ctpMain, cbCommands, "Pick command", JOptionPane.QUESTION_MESSAGE);

		if(document == null){
			JOptionPane.showMessageDialog(null, "First, create a document");
			createTemplatesAction();
		}

		argumentsOfCommand[0] = cbCommands.getSelectedItem();
		argumentsOfCommand[1] = currentDocumentTemplate;
		argumentsOfCommand[2] = document;

		document = (Document) controller.enact(ADD, argumentsOfCommand);

		if (document == null){
			String message =
					 argumentsOfCommand[0] +
					 "\" Latex command, is not allowed in \""
					 + argumentsOfCommand[1] +
					 "\" Latex document.";
			JOptionPane.showMessageDialog(ctpMain, message, "Invalid Latex command input.", JOptionPane.ERROR_MESSAGE);

			return;
		}

		if(versionsManager.isEnabled()){
			enableVersionManagementAction();
		}

		taDocument.setText(document.getContents());
	}

	private Document editContentsAction(){
		if(document == null){
			JOptionPane.showMessageDialog(null, "First, create a document");
			createTemplatesAction();
			return document;
		}

		argumentsOfCommand[0] = document;
		argumentsOfCommand[1] = taDocument.getText();
		argumentsOfCommand[2] = "";

		document = (Document) controller.enact(EDIT, argumentsOfCommand);

		if(versionsManager.isEnabled()){
			enableVersionManagementAction();
		}

		taDocument.setText(document.getContents());

		return document;
	}

	private void createTemplatesVisuals(){
		btnAddCommand.setVisible(true);
		lblCommands.setVisible(true);
		btnRollback.setVisible(true);
		btnSave.setVisible(true);
		btnLoad.setVisible(true);
		btnSubmitChanges.setVisible(true);
	}
}
