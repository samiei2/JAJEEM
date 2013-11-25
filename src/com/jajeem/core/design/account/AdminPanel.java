package com.jajeem.core.design.account;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebTextField;
import com.alee.managers.tooltip.TooltipManager;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.model.Student;
import com.jajeem.core.service.InstructorService;
import com.jajeem.core.service.StudentService;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;
import com.jajeem.util.Config;
import com.jajeem.util.CustomButton;
import com.jajeem.util.JasperReport;
import com.jajeem.util.MultiLineCellRenderer;
import com.jajeem.util.Query;
import com.jajeem.util.StripedTableCellRenderer;
import com.jajeem.util.i18n;
import java.awt.Dimension;
import javax.swing.JButton;


@SuppressWarnings("deprecation")
public class AdminPanel extends CustomAccountFrame{
	Font font = new Font("Arial", Font.BOLD, 12);
	
	private static AdminPanel frame;
	private WebFrame mainFrame;
	@SuppressWarnings("unused")
	private WebPanel contentPane;
	private DatabaseManager databaseDialog;

	private EventList<Course> courseList = new BasicEventList<Course>();
	private EventSelectionModel<Course> courseSelectionModel;

	private EventList<com.jajeem.core.model.Instructor> instructorList = new BasicEventList<com.jajeem.core.model.Instructor>();
	private EventSelectionModel<com.jajeem.core.model.Instructor> instructorSelectionModel;

	private EventList<com.jajeem.core.model.Student> studentList = new BasicEventList<com.jajeem.core.model.Student>();
	private EventSelectionModel<com.jajeem.core.model.Student> studentSelectionModel;
	
	public AdminPanel() throws Exception {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		loadData();
		
		final CustomAccountCheckBox chckbxNewCheckBox = new CustomAccountCheckBox();
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setText("Instructor");
		chckbxNewCheckBox.setForeground(Color.WHITE);
		chckbxNewCheckBox.setFont(font);
		
		
		final CustomAccountCheckBox checkBox = new CustomAccountCheckBox();
		checkBox.setText("Courses");
		checkBox.setForeground(Color.WHITE);
		checkBox.setFont(font);
		
		final CustomAccountCheckBox checkBox_1 = new CustomAccountCheckBox();
		checkBox_1.setText("Students");
		checkBox_1.setForeground(Color.WHITE);
		checkBox_1.setFont(font);
		
		GroupLayout groupLayout = new GroupLayout(getTopContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(chckbxNewCheckBox, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(checkBox, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(checkBox_1, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(checkBox_1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxNewCheckBox, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		getTopContentPane().setLayout(groupLayout);
		
		WebButton webButton = new WebButton();
		
		WebButton webButton_1 = new WebButton();
		
		WebButton webButton_2 = new WebButton();
		GroupLayout groupLayout_2 = new GroupLayout(getCloseContentPane());
		groupLayout_2.setHorizontalGroup(
			groupLayout_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout_2.createSequentialGroup()
					.addContainerGap(17, Short.MAX_VALUE)
					.addComponent(webButton_2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webButton_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(18))
		);
		groupLayout_2.setVerticalGroup(
			groupLayout_2.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout_2.createParallelGroup(Alignment.LEADING)
						.addComponent(webButton_2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButton_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		getCloseContentPane().setLayout(groupLayout_2);
		
		final JPanel cards = new JPanel();
		cards.setOpaque(false);
		GroupLayout groupLayout_1 = new GroupLayout(getMainContentPane());
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(cards, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(cards, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
					.addContainerGap())
		);
		CardLayout cardlayout = new CardLayout(0, 0);
		cards.setLayout(cardlayout);
		
		WebPanel instructorPanel = new WebPanel(); 
		instructorPanel.setOpaque(false);
		instructorPanel.setLayout(new BorderLayout(0, 0));
		instructorPanel.add(initInstructor());
		
		WebPanel coursesPanel = new WebPanel(); 
		coursesPanel.setOpaque(false);
		coursesPanel.setLayout(new BorderLayout(0, 0));
		coursesPanel.add(initCourse());
		
		WebPanel studentPanel = new WebPanel();
		studentPanel.setOpaque(false);
		studentPanel.setLayout(new BorderLayout(0, 0));
		studentPanel.add(initStudent());
		
		
		cards.add(instructorPanel,"instructor");
		cards.add(coursesPanel,"course");
		cards.add(studentPanel,"student");
		getMainContentPane().setLayout(groupLayout_1);
		pack();
		mainFrame = this;
		
		
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxNewCheckBox.setSelected(true);
				checkBox.setSelected(false);
				checkBox_1.setSelected(false);
				CardLayout cl_cards = (CardLayout)(cards.getLayout());
		        cl_cards.show(cards, "instructor");
			}
		});
		
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxNewCheckBox.setSelected(false);
				checkBox.setSelected(true);
				checkBox_1.setSelected(false);
				CardLayout cl_cards = (CardLayout)(cards.getLayout());
		        cl_cards.show(cards, "course");
			}
		});
		
		checkBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkBox.setSelected(false);
				chckbxNewCheckBox.setSelected(false);
				checkBox_1.setSelected(true);
				CardLayout cl_cards = (CardLayout)(cards.getLayout());
		        cl_cards.show(cards, "student");
			}
		});
		
//		setVisible(true);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private void loadData() throws SQLException {

		InstructorService instructorService = new InstructorService();
		ArrayList<com.jajeem.core.model.Instructor> instructorList = instructorService
				.list();
		getInstructorList().addAll(instructorList);

		StudentService studentService = new StudentService();
		ArrayList<com.jajeem.core.model.Student> studentList = studentService
				.list();
		getStudentList().addAll(studentList);

		RoomService rs = new RoomService();
		ArrayList<Course> courseList = rs.getCourseDAO().list();

		for (Course course : courseList) {
			Instructor ins = instructorService
					.getById(course.getInstructorId());

			if (ins != null) {
				course.setInstructor(instructorService.getById(
						course.getInstructorId()).getUsername());
				getCourseList().add(course);
			} else {
				course.setInstructor("");
				getCourseList().add(course);
			}
		}

	}

	@SuppressWarnings({"unused" })
	private WebPanel initCourse() throws Exception {

		final WebPanel panel = new WebPanel();
		panel.setOpaque(false);
		panel.setMargin(new Insets(5, 5, 5, 5));

		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTable courseTable = new JTable();
		TooltipManager.setTooltip(courseTable,
				i18n.getParam("Select a course and push edit button to edit"));

		jScrollPane1.setViewportView(courseTable);

		WebPanel bottomPanel = new WebPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setSize(new Dimension(0, 60));
		bottomPanel.add(buttonPanel);

		CustomAccountButton addButton = new CustomAccountButton("/icons/noa_en/accountadd.png");
		addButton.setMargin(new Insets(0, 5, 0, 0));
		addButton.setHorizontalAlignment(SwingConstants.LEFT);
		addButton.setHorizontalTextPosition(SwingConstants.LEFT);
		addButton.setUndecorated(true);
		addButton.setText(i18n.getParam("Add"));
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AddNewCourseDialog(courseList, getInstructorList());
				} catch (Exception e1) {
					JajeemExcetionHandler.logError(e1);
					e1.printStackTrace();
				}
			}
		});

		CustomAccountButton deleteButton = new CustomAccountButton("/icons/noa_en/accountdelete.png");
		deleteButton.setHorizontalTextPosition(SwingConstants.LEFT);
		deleteButton.setHorizontalAlignment(SwingConstants.LEFT);
		deleteButton.setMargin(new Insets(0, 5, 0, 0));
		deleteButton.setUndecorated(true);
		deleteButton.setSize(50, 30);
		deleteButton.setText(i18n.getParam("Delete"));
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) throws HeadlessException {

				int resp;
				try {
					resp = WebOptionPane.showConfirmDialog(
							panel,
							i18n.getParam("Do you want to Delete selected item(s)?"),
							i18n.getParam("Confirm"),
							WebOptionPane.YES_NO_OPTION,
							WebOptionPane.QUESTION_MESSAGE);
					if (resp == 0) {
						if (!courseSelectionModel.isSelectionEmpty()) {
							RoomService rs = new RoomService();
							for (Course course : courseSelectionModel
									.getSelected()) {
								try {
									if (course.getInstructorId() != 0) {
										rs.getCourseDAO().delete(course);
									}
								} catch (SQLException e1) {
									JajeemExcetionHandler.logError(e1);
									e1.printStackTrace();
								}
							}
							getCourseList().removeAll(
									courseSelectionModel.getSelected());

						}
					}
				} catch (Exception e2) {
					JajeemExcetionHandler.logError(e2);
					e2.printStackTrace();
				}
			}
		});

		WebButton editButton = new WebButton("Edit");
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!courseSelectionModel.isSelectionEmpty()) {
					Course course = courseSelectionModel.getSelected().get(0);
					try {
						new AddNewCourseDialog(courseList, course,
								courseSelectionModel.getSelected(),
								getInstructorList());
					} catch (Exception e1) {
						JajeemExcetionHandler.logError(e1);
						e1.printStackTrace();
					}
				}
			}
		});

		WebButton studentButton = new WebButton(i18n.getParam("Details"));
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addGap(32)
					.addComponent(editButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(studentButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(10)
							.addComponent(editButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(studentButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		buttonPanel.setLayout(gl_buttonPanel);
		studentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!courseSelectionModel.isSelectionEmpty()) {
					if (courseSelectionModel.getSelected().size() > 1) {
						WebOptionPane.showMessageDialog(frame,
								"Please select one course.", "Message",
								WebOptionPane.INFORMATION_MESSAGE);
					} else {
						Course course = courseSelectionModel.getSelected().get(
								0);
						try {
//							new CourseStudentDialog(course, true);
							new StudentsAndQuizListDialog(course,true,course.getId(),"course");
						} catch (Exception e1) {
							JajeemExcetionHandler.logError(e1);
							e1.printStackTrace();
						}
					}
				}
			}
		});

		WebButton instructorButton = new WebButton(i18n.getParam("Instructors"));
		// buttonPanel.add(instructorButton);
		instructorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!courseSelectionModel.isSelectionEmpty()) {
					if (courseSelectionModel.getSelected().size() > 1) {
						WebOptionPane.showMessageDialog(frame,
								"Please select one course.", "Message",
								WebOptionPane.INFORMATION_MESSAGE);
					} else {
						Course course = courseSelectionModel.getSelected().get(
								0);
						try {
							new InstructorCourseDialog(course);
						} catch (SQLException e1) {
							JajeemExcetionHandler.logError(e1);
							e1.printStackTrace();
						}
					}
				}
			}
		});

//		WebButton quizButton = new WebButton(i18n.getParam("Quizzes"));
//		buttonPanel.add(quizButton);
//		quizButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (!courseSelectionModel.isSelectionEmpty()) {
//					if (courseSelectionModel.getSelected().size() > 1) {
//						WebOptionPane.showMessageDialog(frame,
//								"Please select one course.", "Message",
//								WebOptionPane.INFORMATION_MESSAGE);
//					} else {
//						Course course = courseSelectionModel.getSelected().get(
//								0);
//						new Quiz_OpenDialog(course.getId(), "course");
//					}
//				}
//			}
//		});

		JPanel paginationPanel = new JPanel();
		paginationPanel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) paginationPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		bottomPanel.add(paginationPanel);

		WebButton nextButton = new WebButton(i18n.getParam("Export to pdf"));
		paginationPanel.add(nextButton);
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm")
						.format(Calendar.getInstance().getTime());
				JasperReport.generate("Courses", "CourseList_" + timeStamp,
						Query.courses());
			}
		});

		WebPanel topPanel = new WebPanel();
		topPanel.setOpaque(false);
		topPanel.setMargin(new Insets(7, 2, 7, 2));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JLabel filterLabel = new JLabel(i18n.getParam("Search") + ": ");
		topPanel.add(filterLabel);
		WebTextField courseFilterTF = new WebTextField();
		topPanel.add(courseFilterTF);

		TextFilterator<Course> personTextFilterator = new TextFilterator<Course>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void getFilterStrings(java.util.List list, Course c) {
				// field you want to enable filter
				list.add(c.getId());
				list.add(c.getName());
				list.add(c.getInstructor());
				list.add(c.getLevel());

				list.add(c.getDay1() + ", " + c.getStartTime1() + "-"
						+ c.getEndTime1());
				list.add(c.getDay2() + ", " + c.getStartTime2() + "-"
						+ c.getEndTime2());
				list.add(c.getDay3() + ", " + c.getStartTime3() + "-"
						+ c.getEndTime3());
				list.add(c.getDay4() + ", " + c.getStartTime4() + "-"
						+ c.getEndTime4());
				list.add(c.getDay5() + ", " + c.getStartTime5() + "-"
						+ c.getEndTime1());

				list.add(c.getSession());
				list.add(c.getClassType());

				Date startDate = new Date(c.getStartDate());
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				list.add(dt.format(startDate));
			}
		};

		// Table Configuration
		MatcherEditor<Course> textMatcherEditor = new TextComponentMatcherEditor<Course>(
				courseFilterTF, personTextFilterator);
		FilterList<Course> filterList = new FilterList<Course>(getCourseList(),
				textMatcherEditor);
		SortedList<Course> sortedCourse = new SortedList<Course>(filterList,
				null);
		AdvancedTableModel<Course> model = GlazedListsSwing
				.eventTableModelWithThreadProxyList(sortedCourse,
						new CourseTableFormat());

		courseSelectionModel = new EventSelectionModel<Course>(filterList);
		courseTable.setSelectionModel(courseSelectionModel);
		courseTable.setModel(model);
		TableComparatorChooser<Course> tableSorter = TableComparatorChooser
				.install(courseTable, sortedCourse,
						TableComparatorChooser.SINGLE_COLUMN);

		MultiLineCellRenderer multiLineRenderer = new MultiLineCellRenderer(
				SwingConstants.LEFT, SwingConstants.CENTER);

		TableColumnModel tcm = courseTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(10);
		tcm.getColumn(4).setPreferredWidth(10);
		tcm.getColumn(6).setPreferredWidth(10);
		tcm.getColumn(7).setCellRenderer(multiLineRenderer);
		courseTable.setRowHeight(60);

		StripedTableCellRenderer.installInTable(courseTable, Color.lightGray,
				Color.white, null, null);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 723, GroupLayout.PREFERRED_SIZE)
						.addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, 723, GroupLayout.PREFERRED_SIZE)
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 723, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel);
		
		courseTable.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			         JTable target = (JTable)e.getSource();
			         int row = target.getSelectedRow();
			         int column = target.getSelectedColumn();
			         if (!courseSelectionModel.isSelectionEmpty()) {
							if (courseSelectionModel.getSelected().size() > 1) {
								WebOptionPane.showMessageDialog(frame,
										"Please select one course.", "Message",
										WebOptionPane.INFORMATION_MESSAGE);
							} else {
								Course course = courseSelectionModel.getSelected().get(
										0);
								try {
//									new CourseStudentDialog(course, true);
									new StudentsAndQuizListDialog(course,true,course.getId(),"course");
								} catch (Exception e1) {
									JajeemExcetionHandler.logError(e1);
									e1.printStackTrace();
								}
					      }
			          }
			      }
			   }
			});

		return panel;
	}

	@SuppressWarnings({ "unused" })
	private WebPanel initInstructor() throws Exception {

		final WebPanel panel = new WebPanel();
		panel.setOpaque(false);
		panel.setMargin(new Insets(5, 5, 5, 5));

		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTable instructorTable = new JTable();
		TooltipManager.setTooltip(instructorTable,
				i18n.getParam("Click on a cell to edit"));

		jScrollPane1.setViewportView(instructorTable);

		WebPanel bottomPanel = new WebPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		bottomPanel.add(buttonPanel);

		CustomAccountButton addButton = new CustomAccountButton("/icons/noa_en/accountadd.png");
		addButton.setText(i18n.getParam("Add"));
		addButton.setMargin(new Insets(0, 5, 0, 0));
		addButton.setHorizontalTextPosition(SwingConstants.LEFT);
		addButton.setHorizontalAlignment(SwingConstants.LEFT);
		addButton.setUndecorated(true);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AddNewInstructorDialog(getInstructorList());
				} catch (Exception e1) {
					JajeemExcetionHandler.logError(e1);
					e1.printStackTrace();
				}
			}
		});

		CustomAccountButton deleteButton = new CustomAccountButton("/icons/noa_en/accountdelete.png");
		deleteButton.setHorizontalTextPosition(SwingConstants.LEFT);
		deleteButton.setHorizontalAlignment(SwingConstants.LEFT);
		deleteButton.setMargin(new Insets(0, 5, 0, 0));
		deleteButton.setUndecorated(true);
		deleteButton.setText(i18n.getParam("delete"));

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int resp = WebOptionPane.showConfirmDialog(panel,
						"Do you want to Delete selected item(s)?", "Confirm",
						WebOptionPane.YES_NO_OPTION,
						WebOptionPane.QUESTION_MESSAGE);
				if (resp == 0) {
					if (!instructorSelectionModel.isSelectionEmpty()) {
						InstructorService insService = new InstructorService();
						for (Instructor instructor : instructorSelectionModel
								.getSelected()) {
							try {
								if (instructor.getUsername().equals("admin")) {
									WebOptionPane.showMessageDialog(
											getRootPane(),
											"You cannot delete admin account!",
											"Error",
											WebOptionPane.ERROR_MESSAGE);
								} else {
									insService.delete(instructor);
								}
							} catch (SQLException e1) {
								JajeemExcetionHandler.logError(e1);
								e1.printStackTrace();
							}
						}
						getInstructorList().removeAll(
								instructorSelectionModel.getSelected());

					}
				}
			}
		});

		WebButton quizButton = new WebButton(i18n.getParam("Quizzes"));
		quizButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!instructorSelectionModel.isSelectionEmpty()) {
					if (instructorSelectionModel.getSelected().size() > 1) {
						WebOptionPane.showMessageDialog(frame,
								"Please select one instructor.", "Message",
								WebOptionPane.INFORMATION_MESSAGE);
					} else {
						Instructor instructor = instructorSelectionModel
								.getSelected().get(0);
						new Quiz_OpenDialog(instructor.getId(), "instructor");
					}
				}
			}
		});
		
		WebButton databaseManager = new WebButton(i18n.getParam("Database"));
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(quizButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(databaseManager, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(databaseManager, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(quizButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		buttonPanel.setLayout(gl_buttonPanel);
		databaseManager.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(databaseDialog==null){
					DatabaseManager manager = new DatabaseManager(mainFrame);
					databaseDialog = manager;
					manager.setVisible(true);
				}
				else{
					databaseDialog.toFront();
				}
			}
			
		});

		JPanel paginationPanel = new JPanel();
		paginationPanel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) paginationPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		bottomPanel.add(paginationPanel);

		WebButton nextButton = new WebButton(i18n.getParam("Export to pdf"));
		paginationPanel.add(nextButton);
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm")
						.format(Calendar.getInstance().getTime());
				JasperReport.generate("Instructors", "InstructorsList_"
						+ timeStamp, Query.instructors());
			}
		});

		WebPanel topPanel = new WebPanel();
		topPanel.setOpaque(false);
		topPanel.setMargin(new Insets(7, 2, 7, 2));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JLabel filterLabel = new JLabel(i18n.getParam("Search") + ": ");
		topPanel.add(filterLabel);
		WebTextField instructorFilterTF = new WebTextField();
		topPanel.add(instructorFilterTF);

		TextFilterator<Instructor> personTextFilterator = new TextFilterator<Instructor>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void getFilterStrings(java.util.List list, Instructor i) {
				// field you want to enable filter
				list.add(i.getId());
				list.add(i.getFirstName());
				list.add(i.getLastName());
				list.add(i.getUsername());
			}
		};

		// Table Configuration
		MatcherEditor<Instructor> textMatcherEditor = new TextComponentMatcherEditor<Instructor>(
				instructorFilterTF, personTextFilterator);
		FilterList<Instructor> filterList = new FilterList<Instructor>(
				getInstructorList(), textMatcherEditor);
		SortedList<Instructor> sortedInstructor = new SortedList<Instructor>(
				filterList, null);
		AdvancedTableModel<Instructor> model = GlazedListsSwing
				.eventTableModelWithThreadProxyList(sortedInstructor,
						new InstructorTableFormat());

		instructorSelectionModel = new EventSelectionModel<Instructor>(
				filterList);
		instructorTable.setSelectionModel(instructorSelectionModel);
		instructorTable.setModel(model);
		TableComparatorChooser<Instructor> tableSorter = TableComparatorChooser
				.install(instructorTable, sortedInstructor,
						TableComparatorChooser.SINGLE_COLUMN);

		StripedTableCellRenderer.installInTable(instructorTable,
				Color.lightGray, Color.white, null, null);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 733, GroupLayout.PREFERRED_SIZE)
						.addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, 733, GroupLayout.PREFERRED_SIZE)
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 733, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 407, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bottomPanel, GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		instructorTable.getColumnModel().getColumn(0).setPreferredWidth(10);

		return panel;
	}

	@SuppressWarnings("unused")
	private WebPanel initStudent() throws Exception {

		final WebPanel panel = new WebPanel();
		panel.setMargin(new Insets(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTable studentTable = new JTable();
		TooltipManager.setTooltip(studentTable,
				i18n.getParam("Click on a cell to edit"));

		jScrollPane1.setViewportView(studentTable);
		panel.add(jScrollPane1);

		WebPanel bottomPanel = new WebPanel();
		panel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		bottomPanel.add(buttonPanel);

		WebButton addButton = new WebButton(i18n.getParam("Add"));
		buttonPanel.add(addButton);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AddNewStudentDialog(getStudentList());
				} catch (Exception e1) {
					JajeemExcetionHandler.logError(e1);
					e1.printStackTrace();
				}
			}
		});

		WebButton deleteButton = new WebButton(i18n.getParam("Delete"));
		buttonPanel.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int resp = WebOptionPane.showConfirmDialog(panel,
						"Do you want to Delete selected item(s)?", "Confirm",
						WebOptionPane.YES_NO_OPTION,
						WebOptionPane.QUESTION_MESSAGE);
				if (resp == 0) {
					if (!studentSelectionModel.isSelectionEmpty()) {

						StudentService stuService = new StudentService();
						for (Student student : studentSelectionModel
								.getSelected()) {
							try {
								stuService.delete(student);
							} catch (SQLException e1) {
								JajeemExcetionHandler.logError(e1);
								e1.printStackTrace();
							}
						}
						getStudentList().removeAll(
								studentSelectionModel.getSelected());

					}
				}
			}
		});

		WebButton courseButton = new WebButton(i18n.getParam("Courses"));
		buttonPanel.add(courseButton);
		courseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!studentSelectionModel.isSelectionEmpty()) {
					if (studentSelectionModel.getSelected().size() > 1) {
						WebOptionPane.showMessageDialog(frame,
								"Please select one student.", "Message",
								WebOptionPane.INFORMATION_MESSAGE);
					} else {
						Student student = studentSelectionModel.getSelected()
								.get(0);
						try {
							new StudentCourseDialog(student);
						} catch (Exception e1) {
							JajeemExcetionHandler.logError(e1);
							e1.printStackTrace();
						}
					}
				}
			}
		});

		JPanel paginationPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) paginationPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		bottomPanel.add(paginationPanel);

		WebButton nextButton = new WebButton(i18n.getParam("Export to pdf"));
		paginationPanel.add(nextButton);
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm")
						.format(Calendar.getInstance().getTime());
				JasperReport.generate("Students", "StudentList_" + timeStamp,
						Query.students());
			}
		});

		WebPanel topPanel = new WebPanel();
		topPanel.setMargin(new Insets(7, 2, 7, 2));
		panel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JLabel filterLabel = new JLabel(i18n.getParam("Search") + ": ");
		topPanel.add(filterLabel);
		WebTextField studentFilterTF = new WebTextField();
		topPanel.add(studentFilterTF);

		TextFilterator<Student> personTextFilterator = new TextFilterator<Student>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void getFilterStrings(java.util.List list, Student s) {
				// field you want to enable filter
				list.add(s.getId());
				list.add(s.getFirstName());
				list.add(s.getLastName());
				list.add(s.getUsername());
			}
		};

		// Table Configuration
		MatcherEditor<Student> textMatcherEditor = new TextComponentMatcherEditor<Student>(
				studentFilterTF, personTextFilterator);
		FilterList<Student> filterList = new FilterList<Student>(
				getStudentList(), textMatcherEditor);
		SortedList<Student> sortedStudent = new SortedList<Student>(filterList,
				null);
		AdvancedTableModel<Student> model = GlazedListsSwing
				.eventTableModelWithThreadProxyList(sortedStudent,
						new StudentTableFormat());

		studentSelectionModel = new EventSelectionModel<Student>(filterList);
		studentTable.setSelectionModel(studentSelectionModel);
		studentTable.setModel(model);
		TableComparatorChooser<Student> tableSorter = TableComparatorChooser
				.install(studentTable, sortedStudent,
						TableComparatorChooser.SINGLE_COLUMN);

		StripedTableCellRenderer.installInTable(studentTable, Color.lightGray,
				Color.white, null, null);
		studentTable.getColumnModel().getColumn(0).setPreferredWidth(10);

		return panel;
	}

	public class CourseTableFormat implements TableFormat<Course>,
			WritableTableFormat<Course> {

		public int getColumnCount() {
			return 8;
		}

		public String getColumnName(int column) {
			try {
				if (column == 0)
					return i18n.getParam("ID");
				if (column == 1)
					return i18n.getParam("Course Name");
				else if (column == 2)
					return i18n.getParam("Instructor Name");
				else if (column == 3)
					return i18n.getParam("Class Type");
				else if (column == 4)
					return i18n.getParam("Level");
				else if (column == 5)
					return i18n.getParam("Start Date");
				else if (column == 6)
					return i18n.getParam("Sessions");
				else if (column == 7)
					return i18n.getParam("Weekly Time");
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
				e.printStackTrace();
			}
			throw new IllegalStateException();
		}

		public Object getColumnValue(Course course, int column) {

			if (column == 0)
				return course.getId();
			if (column == 1)
				return course.getName();
			else if (column == 2)
				return course.getInstructor();
			else if (column == 3)
				return course.getClassType();
			else if (column == 4)
				return course.getLevel();
			else if (column == 5) {
				Date startDate = new Date(course.getStartDate());
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				return dt.format(startDate);
			} else if (column == 6)
				return course.getSession();
			else if (column == 7) {
				ArrayList<String> daysCell = new ArrayList<String>();

				for (int i = 0; i < 5; i++) {
					if (i == 0 && !course.getDay1().equals("")) {
						daysCell.add((course.getDay1() + ", "
								+ course.getStartTime1() + "-" + course
								.getEndTime1()));
					} else if (i == 1 && !course.getDay2().equals("")) {
						daysCell.add((course.getDay2() + ", "
								+ course.getStartTime2() + "-" + course
								.getEndTime2()));
					} else if (i == 2 && !course.getDay3().equals("")) {
						daysCell.add((course.getDay3() + ", "
								+ course.getStartTime3() + "-" + course
								.getEndTime3()));
					} else if (i == 3 && !course.getDay4().equals("")) {
						daysCell.add((course.getDay4() + ", "
								+ course.getStartTime4() + "-" + course
								.getEndTime4()));
					} else if (i == 4 && !course.getDay5().equals("")) {
						daysCell.add((course.getDay5() + ", "
								+ course.getStartTime5() + "-" + course
								.getEndTime5()));
					}
				}

				return daysCell.toArray();
			}

			throw new IllegalStateException();
		}

		@Override
		public boolean isEditable(Course baseObject, int column) {
			return false; // which columns to be editable?
		}

		@Override
		public Course setColumnValue(Course baseObject, Object editedValue,
				int column) {
			return baseObject;
		}
	}

	public class InstructorTableFormat implements TableFormat<Instructor>,
			WritableTableFormat<Instructor> {

		public int getColumnCount() {
			return 5;
		}

		public String getColumnName(int column) {
			try {
				if (column == 0)
					return i18n.getParam("ID");
				if (column == 1)

					return i18n.getParam("First name");

				else if (column == 2)
					return i18n.getParam("Last Name");
				else if (column == 3)
					return i18n.getParam("Username");
				else if (column == 4)
					return i18n.getParam("Password");
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
				e.printStackTrace();
			}
			throw new IllegalStateException();
		}

		public Object getColumnValue(Instructor instructor, int column) {

			if (column == 0)
				return instructor.getId();
			if (column == 1)
				return instructor.getFirstName();
			else if (column == 2)
				return instructor.getLastName();
			else if (column == 3)
				return instructor.getUsername();
			else if (column == 4)
				return instructor.getPassword();

			throw new IllegalStateException();
		}

		@Override
		public boolean isEditable(Instructor baseObject, int column) {
			return column > 0; // which columns to be editable?
		}

		@Override
		public Instructor setColumnValue(Instructor baseObject,
				Object editedValue, int column) {

			if (column == 1) {
				baseObject.setFirstName((String) editedValue);
			} else if (column == 2) {
				baseObject.setLastName((String) editedValue);
			} else if (column == 3) {
				if (baseObject.getUsername().equals("admin")) {
					WebOptionPane.showMessageDialog(getRootPane(),
							"You cannot change admin's username!", "Error",
							WebOptionPane.ERROR_MESSAGE);

				} else {
					baseObject.setUsername((String) editedValue);
				}
			} else if (column == 4) {
				baseObject.setPassword((String) editedValue);
			}

			InstructorService insService = new InstructorService();
			try {
				insService.update(baseObject);
			} catch (SQLException e) {
				JajeemExcetionHandler.logError(e);
				e.printStackTrace();
			}

			return baseObject;
		}
	}

	public class StudentTableFormat implements TableFormat<Student>,
			WritableTableFormat<Student> {

		public int getColumnCount() {
			return 5;
		}

		public String getColumnName(int column) {
			try {
				if (column == 0)
					return i18n.getParam("ID");
				if (column == 1)
					return i18n.getParam("First name");
				else if (column == 2)
					return i18n.getParam("Last Name");
				else if (column == 3)
					return i18n.getParam("Username");
				else if (column == 4)
					return i18n.getParam("Password");
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
				e.printStackTrace();
			}
			throw new IllegalStateException();
		}

		public Object getColumnValue(Student student, int column) {

			if (column == 0)
				return student.getId();
			if (column == 1)
				return student.getFirstName();
			else if (column == 2)
				return student.getLastName();
			else if (column == 3)
				return student.getUsername();
			else if (column == 4)
				return student.getPassword();

			throw new IllegalStateException();
		}

		@Override
		public boolean isEditable(Student baseObject, int column) {
			return column > 0;
		}

		@Override
		public Student setColumnValue(Student baseObject, Object editedValue,
				int column) {
			if (column == 1) {
				baseObject.setFirstName((String) editedValue);
			} else if (column == 2) {
				baseObject.setLastName((String) editedValue);
			} else if (column == 3) {
				baseObject.setUsername((String) editedValue);
			} else if (column == 4) {
				baseObject.setPassword((String) editedValue);
			}

			StudentService stuService = new StudentService();
			try {
				stuService.update(baseObject);
			} catch (SQLException e) {
				JajeemExcetionHandler.logError(e);
				e.printStackTrace();
			}

			return baseObject;
		}
	}

	public EventList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(EventList<Course> courseList) {
		this.courseList = courseList;
	}

	public EventList<com.jajeem.core.model.Instructor> getInstructorList() {
		return instructorList;
	}

	public void setInstructorList(
			EventList<com.jajeem.core.model.Instructor> instructorList) {
		this.instructorList = instructorList;
	}

	public EventSelectionModel<com.jajeem.core.model.Instructor> getInstructorSelectionModel() {
		return instructorSelectionModel;
	}

	public void setInstructorSelectionModel(
			EventSelectionModel<com.jajeem.core.model.Instructor> instructorSelectionModel) {
		this.instructorSelectionModel = instructorSelectionModel;
	}

	public EventList<com.jajeem.core.model.Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(
			EventList<com.jajeem.core.model.Student> studentList) {
		this.studentList = studentList;
	}

	public EventSelectionModel<com.jajeem.core.model.Student> getStudentSelectionModel() {
		return studentSelectionModel;
	}

	public void setStudentSelectionModel(
			EventSelectionModel<com.jajeem.core.model.Student> studentSelectionModel) {
		this.studentSelectionModel = studentSelectionModel;
	}

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Config();
					new i18n();
					new Query();

					frame = new AdminPanel();
					frame.setVisible(true);

				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});
	}
}
