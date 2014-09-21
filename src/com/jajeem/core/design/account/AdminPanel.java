package com.jajeem.core.design.account;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
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
import javax.swing.JOptionPane;
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
import ca.odell.glazedlists.gui.AbstractTableComparatorChooser;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebTextField;
import com.alee.managers.tooltip.TooltipManager;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.model.Student;
import com.jajeem.core.service.InstructorService;
import com.jajeem.core.service.StudentService;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;
import com.jajeem.util.Config;
import com.jajeem.util.JasperReport;
import com.jajeem.util.MultiLineCellRenderer;
import com.jajeem.util.Query;
import com.jajeem.util.StripedTableCellRenderer;
import com.jajeem.util.i18n;
import javax.swing.ImageIcon;

@SuppressWarnings("deprecation")
public class AdminPanel extends CustomAccountFrame {
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
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(5)
						.addComponent(chckbxNewCheckBox,
								GroupLayout.PREFERRED_SIZE, 75,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox, GroupLayout.PREFERRED_SIZE,
								75,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_1, GroupLayout.PREFERRED_SIZE,
								74, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																checkBox,
																GroupLayout.PREFERRED_SIZE,
																34,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																checkBox_1,
																GroupLayout.PREFERRED_SIZE,
																34,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																chckbxNewCheckBox,
																GroupLayout.PREFERRED_SIZE,
																34,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		getTopContentPane().setLayout(groupLayout);

		CustomAccountButton webButtonClose = new CustomAccountButton(
				"/icons/noa_en/close.png");
		webButtonClose.setUndecorated(true);
		webButtonClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		CustomAccountButton webButtonMax = new CustomAccountButton(
				"/icons/noa_en/max.png");
		webButtonMax.setUndecorated(true);
		webButtonMax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setExtendedState(Frame.MAXIMIZED_BOTH);
			}
		});

		CustomAccountButton webButtonMin = new CustomAccountButton(
				"/icons/noa_en/min.png");
		webButtonMin.setUndecorated(true);
		webButtonMin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setState(Frame.ICONIFIED);
			}
		});
		GroupLayout groupLayout_2 = new GroupLayout(getCloseContentPane());
		groupLayout_2.setHorizontalGroup(groupLayout_2.createParallelGroup(
				Alignment.TRAILING).addGroup(
				groupLayout_2
						.createSequentialGroup()
						.addContainerGap(24, Short.MAX_VALUE)
						.addComponent(webButtonMin, GroupLayout.PREFERRED_SIZE,
								18, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(webButtonMax, GroupLayout.PREFERRED_SIZE,
								18, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(webButtonClose,
								GroupLayout.PREFERRED_SIZE, 18,
								GroupLayout.PREFERRED_SIZE).addGap(17)));
		groupLayout_2
				.setVerticalGroup(groupLayout_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout_2
										.createSequentialGroup()
										.addGap(5)
										.addGroup(
												groupLayout_2
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webButtonMin,
																GroupLayout.PREFERRED_SIZE,
																9,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																groupLayout_2
																		.createParallelGroup(
																				Alignment.LEADING)
																		.addComponent(
																				webButtonMax,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				webButtonClose,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		getCloseContentPane().setLayout(groupLayout_2);

		final JPanel cards = new JPanel();
		cards.setOpaque(false);
		GroupLayout groupLayout_1 = new GroupLayout(getMainContentPane());
		groupLayout_1.setHorizontalGroup(groupLayout_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(cards, GroupLayout.DEFAULT_SIZE, 561,
								Short.MAX_VALUE).addContainerGap()));
		groupLayout_1.setVerticalGroup(groupLayout_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(cards, GroupLayout.DEFAULT_SIZE, 231,
								Short.MAX_VALUE).addContainerGap()));
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

		cards.add(instructorPanel, "instructor");
		cards.add(coursesPanel, "course");
		cards.add(studentPanel, "student");
		getMainContentPane().setLayout(groupLayout_1);
		pack();
		mainFrame = this;

		chckbxNewCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chckbxNewCheckBox.setSelected(true);
				checkBox.setSelected(false);
				checkBox_1.setSelected(false);
				CardLayout cl_cards = (CardLayout) (cards.getLayout());
				cl_cards.show(cards, "instructor");
			}
		});

		checkBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chckbxNewCheckBox.setSelected(false);
				checkBox.setSelected(true);
				checkBox_1.setSelected(false);
				CardLayout cl_cards = (CardLayout) (cards.getLayout());
				cl_cards.show(cards, "course");
			}
		});

		checkBox_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkBox.setSelected(false);
				chckbxNewCheckBox.setSelected(false);
				checkBox_1.setSelected(true);
				CardLayout cl_cards = (CardLayout) (cards.getLayout());
				cl_cards.show(cards, "student");
			}
		});

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle rect = ge.getMaximumWindowBounds();
		int x = (int) rect.getMaxX() / 2 - getWidth() / 2;
		int y = (int) ((rect.getMaxY() / 2 - getHeight() / 2));
		setLocation(x, y);
		setVisible(true);
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

	@SuppressWarnings({ "unused" })
	private WebPanel initCourse() throws Exception {

		final WebPanel panel = new WebPanel();
		panel.setOpaque(false);
		panel.setMargin(new Insets(5, 5, 5, 5));

		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane1.setOpaque(false);
		JTable courseTable = new JTable();
		courseTable.setOpaque(false);
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

		CustomAccountButton addButton = new CustomAccountButton(
				"/icons/noa_en/accountadd.png");
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
					JajeemExceptionHandler.logError(e1);
					e1.printStackTrace();
				}
			}
		});

		CustomAccountButton deleteButton = new CustomAccountButton(
				"/icons/noa_en/accountdelete.png");
		deleteButton.setHorizontalTextPosition(SwingConstants.LEFT);
		deleteButton.setHorizontalAlignment(SwingConstants.LEFT);
		deleteButton.setMargin(new Insets(0, 5, 0, 0));
		deleteButton.setUndecorated(true);
		deleteButton.setSize(50, 30);
		deleteButton.setText(i18n.getParam("Delete"));
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) throws HeadlessException {

				int resp;
				try {
					resp = JOptionPane.showConfirmDialog(
							panel,
							i18n.getParam("Do you want to Delete selected item(s)?"),
							i18n.getParam("Confirm"),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
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
									JajeemExceptionHandler.logError(e1);
									e1.printStackTrace();
								}
							}
							getCourseList().removeAll(
									courseSelectionModel.getSelected());

						}
					}
				} catch (Exception e2) {
					JajeemExceptionHandler.logError(e2);
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
						JajeemExceptionHandler.logError(e1);
						e1.printStackTrace();
					}
				}
				else{
					JOptionPane.showMessageDialog(frame,
							"Please select one course.", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		WebButton studentButton = new WebButton(i18n.getParam("Details"));
		studentButton.setHorizontalTextPosition(SwingConstants.LEADING);
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(gl_buttonPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_buttonPanel
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(addButton, GroupLayout.PREFERRED_SIZE,
								90, GroupLayout.PREFERRED_SIZE)
						.addGap(13)
						.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE,
								90, GroupLayout.PREFERRED_SIZE)
						.addGap(24)
						.addComponent(editButton, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(studentButton,
								GroupLayout.PREFERRED_SIZE, 89,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
		gl_buttonPanel
				.setVerticalGroup(gl_buttonPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_buttonPanel
										.createSequentialGroup()
										.addGroup(
												gl_buttonPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_buttonPanel
																		.createSequentialGroup()
																		.addGap(10)
																		.addComponent(
																				editButton,
																				GroupLayout.PREFERRED_SIZE,
																				31,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_buttonPanel
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				studentButton,
																				GroupLayout.PREFERRED_SIZE,
																				31,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_buttonPanel
																		.createSequentialGroup()
																		.addGap(10)
																		.addGroup(
																				gl_buttonPanel
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								deleteButton,
																								GroupLayout.PREFERRED_SIZE,
																								34,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								addButton,
																								GroupLayout.PREFERRED_SIZE,
																								34,
																								GroupLayout.PREFERRED_SIZE))))
										.addContainerGap(12, Short.MAX_VALUE)));
		buttonPanel.setLayout(gl_buttonPanel);
		studentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!courseSelectionModel.isSelectionEmpty()) {
					if (courseSelectionModel.getSelected().size() > 1) {
						JOptionPane.showMessageDialog(frame,
								"Please select one course.", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						Course course = courseSelectionModel.getSelected().get(
								0);
						try {
							// new CourseStudentDialog(course, true);
							new StudentsAndQuizListDialog(course, true, course
									.getId(), "course");
						} catch (Exception e1) {
							JajeemExceptionHandler.logError(e1);
							e1.printStackTrace();
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(frame,
							"Please select one course.", "Message",
							JOptionPane.INFORMATION_MESSAGE);
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
						JOptionPane.showMessageDialog(frame,
								"Please select one course.", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						Course course = courseSelectionModel.getSelected().get(
								0);
						try {
							new InstructorCourseDialog(course);
						} catch (SQLException e1) {
							JajeemExceptionHandler.logError(e1);
							e1.printStackTrace();
						}
					}
				}
			}
		});

		// WebButton quizButton = new WebButton(i18n.getParam("Quizzes"));
		// buttonPanel.add(quizButton);
		// quizButton.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// if (!courseSelectionModel.isSelectionEmpty()) {
		// if (courseSelectionModel.getSelected().size() > 1) {
		// WebOptionPane.showMessageDialog(frame,
		// "Please select one course.", "Message",
		// WebOptionPane.INFORMATION_MESSAGE);
		// } else {
		// Course course = courseSelectionModel.getSelected().get(
		// 0);
		// new Quiz_OpenDialog(course.getId(), "course");
		// }
		// }
		// }
		// });

		JPanel paginationPanel = new JPanel();
		paginationPanel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) paginationPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		bottomPanel.add(paginationPanel);

		WebButton nextButton = new WebButton(i18n.getParam("Export to pdf"));
		nextButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		nextButton.setForeground(Color.DARK_GRAY);
		nextButton.setHorizontalTextPosition(SwingConstants.LEADING);
		nextButton.setIcon(new ImageIcon(new ImageIcon(AdminPanel.class.getResource("/icons/noa_en/pdf.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		nextButton.setUndecorated(true);
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
		courseTable.setSelectionBackground(Color.CYAN);
		TableComparatorChooser<Course> tableSorter = TableComparatorChooser
				.install(courseTable, sortedCourse,
						AbstractTableComparatorChooser.SINGLE_COLUMN);

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
						.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(bottomPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
								.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel);

		courseTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					if (!courseSelectionModel.isSelectionEmpty()) {
						if (courseSelectionModel.getSelected().size() > 1) {
							JOptionPane.showMessageDialog(frame,
									"Please select one course.", "Message",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							Course course = courseSelectionModel.getSelected()
									.get(0);
							try {
								// new CourseStudentDialog(course, true);
								new StudentsAndQuizListDialog(course, true,
										course.getId(), "course");
							} catch (Exception e1) {
								JajeemExceptionHandler.logError(e1);
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

		CustomAccountButton addButton = new CustomAccountButton(
				"/icons/noa_en/accountadd.png");
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
					JajeemExceptionHandler.logError(e1);
					e1.printStackTrace();
				}
			}
		});

		CustomAccountButton deleteButton = new CustomAccountButton(
				"/icons/noa_en/accountdelete.png");
		deleteButton.setHorizontalTextPosition(SwingConstants.LEFT);
		deleteButton.setHorizontalAlignment(SwingConstants.LEFT);
		deleteButton.setMargin(new Insets(0, 5, 0, 0));
		deleteButton.setUndecorated(true);
		deleteButton.setText(i18n.getParam("delete"));

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int resp = JOptionPane
						.showConfirmDialog(panel,
								"Do you want to Delete selected item(s)?",
								"Confirm", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (resp == 0) {
					if (!instructorSelectionModel.isSelectionEmpty()) {
						InstructorService insService = new InstructorService();
						for (Instructor instructor : instructorSelectionModel
								.getSelected()) {
							try {
								if (instructor.getUsername().equals("admin")) {
									JOptionPane.showMessageDialog(
											getRootPane(),
											"You cannot delete admin account!",
											"Error", JOptionPane.ERROR_MESSAGE);
								} else {
									insService.delete(instructor);
								}
							} catch (SQLException e1) {
								JajeemExceptionHandler.logError(e1);
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
						JOptionPane.showMessageDialog(frame,
								"Please select an instructor.", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						Instructor instructor = instructorSelectionModel
								.getSelected().get(0);
						new Quiz_OpenDialog(instructor.getId(), "instructor");
					}
				}
				else{
					JOptionPane.showMessageDialog(frame,
							"Please select and instructor.", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		WebButton databaseManager = new WebButton(i18n.getParam("Database"));
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(gl_buttonPanel.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_buttonPanel
						.createSequentialGroup()
						.addGap(5)
						.addComponent(addButton, GroupLayout.PREFERRED_SIZE,
								90, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE,
								76, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 27,
								Short.MAX_VALUE)
						.addComponent(quizButton, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(databaseManager,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
		gl_buttonPanel
				.setVerticalGroup(gl_buttonPanel
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_buttonPanel
										.createSequentialGroup()
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												gl_buttonPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_buttonPanel
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				databaseManager,
																				GroupLayout.PREFERRED_SIZE,
																				29,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				quizButton,
																				GroupLayout.PREFERRED_SIZE,
																				29,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_buttonPanel
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				addButton,
																				GroupLayout.PREFERRED_SIZE,
																				34,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				deleteButton,
																				GroupLayout.PREFERRED_SIZE,
																				32,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		buttonPanel.setLayout(gl_buttonPanel);
		databaseManager.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (databaseDialog == null) {
					DatabaseManager manager = new DatabaseManager(mainFrame);
					databaseDialog = manager;
					manager.setVisible(true);
				} else {
					databaseDialog.toFront();
				}
			}

		});

		JPanel paginationPanel = new JPanel();
		paginationPanel.setOpaque(false);
		bottomPanel.add(paginationPanel);

		WebButton nextButton = new WebButton(i18n.getParam("Export to pdf"));
		nextButton.setForeground(Color.DARK_GRAY);
		nextButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		nextButton.setHorizontalTextPosition(SwingConstants.LEADING);
		nextButton.setIcon(new ImageIcon(new ImageIcon(AdminPanel.class.getResource("/icons/noa_en/pdf.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		nextButton.setUndecorated(true);
		GroupLayout gl_paginationPanel = new GroupLayout(paginationPanel);
		gl_paginationPanel.setHorizontalGroup(
			gl_paginationPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_paginationPanel.createSequentialGroup()
					.addContainerGap(300, Short.MAX_VALUE)
					.addComponent(nextButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_paginationPanel.setVerticalGroup(
			gl_paginationPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_paginationPanel.createSequentialGroup()
					.addContainerGap(17, Short.MAX_VALUE)
					.addComponent(nextButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		paginationPanel.setLayout(gl_paginationPanel);
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
		instructorTable.setSelectionBackground(Color.CYAN);
		TableComparatorChooser<Instructor> tableSorter = TableComparatorChooser
				.install(instructorTable, sortedInstructor,
						AbstractTableComparatorChooser.SINGLE_COLUMN);

		StripedTableCellRenderer.installInTable(instructorTable,
				Color.lightGray, Color.white, null, null);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(bottomPanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 864, Short.MAX_VALUE)
								.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel);
		instructorTable.getColumnModel().getColumn(0).setPreferredWidth(10);

		return panel;
	}

	@SuppressWarnings("unused")
	private WebPanel initStudent() throws Exception {

		final WebPanel panel = new WebPanel();
		panel.setOpaque(false);
		panel.setMargin(new Insets(5, 5, 5, 5));

		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTable studentTable = new JTable();
		TooltipManager.setTooltip(studentTable,
				i18n.getParam("Click on a cell to edit"));

		jScrollPane1.setViewportView(studentTable);

		WebPanel bottomPanel = new WebPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		bottomPanel.add(buttonPanel);

		CustomAccountButton addButton = new CustomAccountButton(
				"/icons/noa_en/accountadd.png");
		addButton.setMargin(new Insets(0, 5, 0, 0));
		addButton.setHorizontalAlignment(SwingConstants.LEFT);
		addButton.setHorizontalTextPosition(SwingConstants.LEFT);
		addButton.setUndecorated(true);
		addButton.setText(i18n.getParam("Add"));
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AddNewStudentDialog(getStudentList());
				} catch (Exception e1) {
					JajeemExceptionHandler.logError(e1);
					e1.printStackTrace();
				}
			}
		});

		CustomAccountButton deleteButton = new CustomAccountButton(
				"/icons/noa_en/accountdelete.png");
		deleteButton.setMargin(new Insets(0, 5, 0, 0));
		deleteButton.setHorizontalTextPosition(SwingConstants.LEFT);
		deleteButton.setHorizontalAlignment(SwingConstants.LEFT);
		deleteButton.setUndecorated(true);
		deleteButton.setText(i18n.getParam("Delete"));
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int resp = JOptionPane
						.showConfirmDialog(panel,
								"Do you want to Delete selected item(s)?",
								"Confirm", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (resp == 0) {
					if (!studentSelectionModel.isSelectionEmpty()) {

						StudentService stuService = new StudentService();
						for (Student student : studentSelectionModel
								.getSelected()) {
							try {
								stuService.delete(student);
							} catch (SQLException e1) {
								JajeemExceptionHandler.logError(e1);
								e1.printStackTrace();
							}
						}
						getStudentList().removeAll(
								studentSelectionModel.getSelected());

					}
				}
			}
		});

		CustomAccountButton courseButton = new CustomAccountButton(
				"/icons/noa_en/accountcourse.png");
		courseButton.setMargin(new Insets(0, 5, 0, 0));
		courseButton.setHorizontalTextPosition(SwingConstants.LEFT);
		courseButton.setHorizontalAlignment(SwingConstants.LEFT);
		courseButton.setText(i18n.getParam("Courses"));
		courseButton.setUndecorated(true);
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGap(4)
					.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(courseButton, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addGap(142))
		);
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addComponent(courseButton, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		buttonPanel.setLayout(gl_buttonPanel);
		courseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!studentSelectionModel.isSelectionEmpty()) {
					if (studentSelectionModel.getSelected().size() > 1) {
						JOptionPane.showMessageDialog(frame,
								"Please select one student.", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						Student student = studentSelectionModel.getSelected()
								.get(0);
						try {
							new StudentCourseDialog(student);
						} catch (Exception e1) {
							JajeemExceptionHandler.logError(e1);
							e1.printStackTrace();
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(frame,
							"Please select one student.", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		JPanel paginationPanel = new JPanel();
		paginationPanel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) paginationPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		bottomPanel.add(paginationPanel);

		WebButton nextButton = new WebButton(i18n.getParam("Export to pdf"));
		nextButton.setForeground(Color.DARK_GRAY);
		nextButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		nextButton.setHorizontalTextPosition(SwingConstants.LEADING);
		nextButton.setIcon(new ImageIcon(new ImageIcon(AdminPanel.class.getResource("/icons/noa_en/pdf.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		nextButton.setUndecorated(true);
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
		topPanel.setOpaque(false);
		topPanel.setMargin(new Insets(7, 2, 7, 2));
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
		studentTable.setSelectionBackground(Color.CYAN);
		TableComparatorChooser<Student> tableSorter = TableComparatorChooser
				.install(studentTable, sortedStudent,
						AbstractTableComparatorChooser.SINGLE_COLUMN);

		StripedTableCellRenderer.installInTable(studentTable, Color.lightGray,
				Color.white, null, null);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(topPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(bottomPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
								.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
					.addGap(7)
					.addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel);
		studentTable.getColumnModel().getColumn(0).setPreferredWidth(10);

		return panel;
	}

	public class CourseTableFormat implements TableFormat<Course>,
			WritableTableFormat<Course> {

		@Override
		public int getColumnCount() {
			return 8;
		}

		@Override
		public String getColumnName(int column) {
			try {
				if (column == 0) {
					return i18n.getParam("ID");
				}
				if (column == 1) {
					return i18n.getParam("Course Name");
				} else if (column == 2) {
					return i18n.getParam("Instructor Name");
				} else if (column == 3) {
					return i18n.getParam("Class Type");
				} else if (column == 4) {
					return i18n.getParam("Level");
				} else if (column == 5) {
					return i18n.getParam("Start Date");
				} else if (column == 6) {
					return i18n.getParam("Sessions");
				} else if (column == 7) {
					return i18n.getParam("Weekly Time");
				}
			} catch (Exception e) {
				JajeemExceptionHandler.logError(e);
				e.printStackTrace();
			}
			throw new IllegalStateException();
		}

		@Override
		public Object getColumnValue(Course course, int column) {

			if (column == 0) {
				return course.getId();
			}
			if (column == 1) {
				return course.getName();
			} else if (column == 2) {
				return course.getInstructor();
			} else if (column == 3) {
				return course.getClassType();
			} else if (column == 4) {
				return course.getLevel();
			} else if (column == 5) {
				Date startDate = new Date(course.getStartDate());
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				return dt.format(startDate);
			} else if (column == 6) {
				return course.getSession();
			} else if (column == 7) {
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

		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public String getColumnName(int column) {
			try {
				if (column == 0) {
					return i18n.getParam("ID");
				}
				if (column == 1) {
					return i18n.getParam("First name");
				} else if (column == 2) {
					return i18n.getParam("Last Name");
				} else if (column == 3) {
					return i18n.getParam("Username");
				} else if (column == 4) {
					return i18n.getParam("Password");
				}
			} catch (Exception e) {
				JajeemExceptionHandler.logError(e);
				e.printStackTrace();
			}
			throw new IllegalStateException();
		}

		@Override
		public Object getColumnValue(Instructor instructor, int column) {

			if (column == 0) {
				return instructor.getId();
			}
			if (column == 1) {
				return instructor.getFirstName();
			} else if (column == 2) {
				return instructor.getLastName();
			} else if (column == 3) {
				return instructor.getUsername();
			} else if (column == 4) {
				return instructor.getPassword();
			}

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
					JOptionPane.showMessageDialog(getRootPane(),
							"You cannot change admin's username!", "Error",
							JOptionPane.ERROR_MESSAGE);

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
				JajeemExceptionHandler.logError(e);
				e.printStackTrace();
			}

			return baseObject;
		}
	}

	public class StudentTableFormat implements TableFormat<Student>,
			WritableTableFormat<Student> {

		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public String getColumnName(int column) {
			try {
				if (column == 0) {
					return i18n.getParam("ID");
				}
				if (column == 1) {
					return i18n.getParam("First name");
				} else if (column == 2) {
					return i18n.getParam("Last Name");
				} else if (column == 3) {
					return i18n.getParam("Username");
				} else if (column == 4) {
					return i18n.getParam("Password");
				}
			} catch (Exception e) {
				JajeemExceptionHandler.logError(e);
				e.printStackTrace();
			}
			throw new IllegalStateException();
		}

		@Override
		public Object getColumnValue(Student student, int column) {

			if (column == 0) {
				return student.getId();
			}
			if (column == 1) {
				return student.getFirstName();
			} else if (column == 2) {
				return student.getLastName();
			} else if (column == 3) {
				return student.getUsername();
			} else if (column == 4) {
				return student.getPassword();
			}

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
				JajeemExceptionHandler.logError(e);
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
			@Override
			public void run() {
				try {
					new Config();
					new i18n();
					new Query();

					frame = new AdminPanel();
					frame.setVisible(true);

				} catch (Exception e) {
					JajeemExceptionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});
	}
}
