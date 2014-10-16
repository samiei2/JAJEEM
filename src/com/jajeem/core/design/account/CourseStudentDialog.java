package com.jajeem.core.design.account;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

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
import com.alee.laf.text.WebTextField;
import com.jajeem.core.model.Student;
import com.jajeem.core.model.StudentCourse;
import com.jajeem.core.service.StudentCourseService;
import com.jajeem.room.model.Course;
import com.jajeem.util.JasperReport;
import com.jajeem.util.Query;
import com.jajeem.util.StripedTableCellRenderer;
import com.jajeem.util.i18n;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class CourseStudentDialog extends BaseAccountFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private final JPanel contentPanel = new JPanel();

	private EventList<com.jajeem.core.model.Student> studentList = new BasicEventList<com.jajeem.core.model.Student>();
	private EventSelectionModel<com.jajeem.core.model.Student> studentSelectionModel;
	private Course course;
	private StudentCourseService studentCourseService = new StudentCourseService();

	/**
	 * Create the dialog.
	 * 
	 * @throws Exception
	 */
	public CourseStudentDialog(final Course course, boolean isAdmin)
			throws Exception {
		setTitle("Students");
		this.course = course;
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 625, 717);
		getMainContentPane().setLayout(new BorderLayout());
//		contentPanel.setLayout(new FlowLayout());
//		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setOpaque(false);
			getMainContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JPanel panel = new JPanel();
				panel.setOpaque(false);
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.LEADING);
				buttonPane.add(panel);
				{
					WebButton addButton = new WebButton("Add");
					if (isAdmin) {
						panel.add(addButton);
					}
					addButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							try {
								// new StudentDialog(frame);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
				{
					WebButton deleteButton = new WebButton("Delete");
					if (isAdmin) {
						panel.add(deleteButton);
					}
					deleteButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							int resp = JOptionPane.showConfirmDialog(
									null,
									"Do you want to Delete selected item(s)?",
									"Confirm", JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE);
							if (resp == 0) {
								if (!studentSelectionModel.isSelectionEmpty()) {
									for (Student student : studentSelectionModel
											.getSelected()) {
										try {
											studentCourseService.delete(
													student.getId(),
													course.getId());
										} catch (SQLException e1) {
											e1.printStackTrace();
										}
									}
									getStudentList()
											.removeAll(
													studentSelectionModel
															.getSelected());

								}
							}
						}
					});
				}
			}
			{
				JPanel panel = new JPanel();
				panel.setOpaque(false);
				buttonPane.add(panel);
				{
					CustomAccountButton okButton = new CustomAccountButton("/icons/noa_en/accountpdf.png");
					okButton.setUndecorated(true);
					GroupLayout gl_panel = new GroupLayout(panel);
					gl_panel.setHorizontalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
								.addContainerGap(227, Short.MAX_VALUE)
								.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
					);
					gl_panel.setVerticalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(11, Short.MAX_VALUE))
					);
					panel.setLayout(gl_panel);
					okButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							String timeStamp = new SimpleDateFormat(
									"yyyy-MM-dd_HH-mm").format(Calendar
									.getInstance().getTime());
							JasperReport.generate("StudentsByCourse",
									course.getName() + "_" + course.getId()
											+ "_" + timeStamp,
									Query.studentByCourse(course.getId()));
						}
					});
				}
			}
		}

		loadData();
		getMainContentPane().add(initStudent());
		pack();
	}

	private void loadData() throws SQLException {
		ArrayList<com.jajeem.core.model.Student> studentList = studentCourseService
				.getcourseStudentsById(course.getId());
		getStudentList().addAll(studentList);
	}

	public void addStudents(EventList<Student> stuList) throws SQLException {
		studentList.addAll(stuList);
		StudentCourse sc = new StudentCourse();
		for (Student student : stuList) {
			sc.setCourseId(course.getId());
			sc.setStudentId(student.getId());
			sc.setScore(0);
			studentCourseService.create(sc);
		}
	}

	@SuppressWarnings("deprecation")
	private WebPanel initStudent() {

		final WebPanel panel = new WebPanel();
		panel.setOpaque(false);
		panel.setMargin(new Insets(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTable studentTable = new JTable();

		jScrollPane1.setViewportView(studentTable);
		panel.add(jScrollPane1);

		WebPanel topPanel = new WebPanel();
		topPanel.setOpaque(false);
		topPanel.setMargin(new Insets(7, 2, 7, 2));
		panel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JLabel filterLabel = new JLabel("Search: ");
		topPanel.add(filterLabel);
		WebTextField studentFilterTF = new WebTextField();
		topPanel.add(studentFilterTF);

		TextFilterator<Student> personTextFilterator = new TextFilterator<Student>() {
			@SuppressWarnings("unchecked")
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
		TableComparatorChooser.install(studentTable, sortedStudent,
				AbstractTableComparatorChooser.SINGLE_COLUMN);

		StripedTableCellRenderer.installInTable(studentTable, Color.lightGray,
				Color.white, null, null);
		studentTable.getColumnModel().getColumn(0).setPreferredWidth(10);

		return panel;
	}

	public class StudentTableFormat implements TableFormat<Student>,
			WritableTableFormat<Student> {

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public String getColumnName(int column) {
			if (column == 0) {
				return "ID";
			}
			if (column == 1) {
				return "First name";
			} else if (column == 2) {
				return "Last Name";
			} else if (column == 3) {
				return "Username";
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
			}

			throw new IllegalStateException();
		}

		@Override
		public boolean isEditable(Student baseObject, int column) {
			return false;
		}

		@Override
		public Student setColumnValue(Student baseObject, Object editedValue,
				int column) {
			return baseObject;
		}
	}

	public EventList<com.jajeem.core.model.Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(
			EventList<com.jajeem.core.model.Student> studentList) {
		this.studentList = studentList;
	}
}
