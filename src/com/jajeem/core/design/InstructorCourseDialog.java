package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

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
import com.alee.laf.text.WebTextField;
import com.alee.managers.tooltip.TooltipManager;
import com.jajeem.core.design.AdminPanel.InstructorTableFormat;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.service.InstructorService;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;
import com.jajeem.util.StripedTableCellRenderer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class InstructorCourseDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private EventList<com.jajeem.core.model.Instructor> instructorList = new BasicEventList<com.jajeem.core.model.Instructor>();
	private EventSelectionModel<com.jajeem.core.model.Instructor> instructorSelectionModel;
	private Course course;
	private InstructorService instructorService = new InstructorService();

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 */
	public InstructorCourseDialog(final Course course) throws SQLException {
		setTitle("Instructors");
		this.course = course;
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 610, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		final InstructorCourseDialog frame = this;
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JPanel panel = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.LEADING);
				buttonPane.add(panel);
			}
			{
				JPanel panel = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.TRAILING);
				buttonPane.add(panel);
				{
					WebButton okButton = new WebButton("Ok");
					panel.add(okButton);
					okButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							dispose();
						}
					});
				}
			}
		}

		loadData();
		getContentPane().add(initInstructor());
	}

	private void loadData() throws SQLException {
		ArrayList<com.jajeem.core.model.Instructor> instructorList = instructorService
				.getByCourseId(course.getId());
		getInstructorList().addAll(instructorList);
	}

	@SuppressWarnings("deprecation")
	private WebPanel initInstructor() {

		final WebPanel panel = new WebPanel();
		panel.setMargin(new Insets(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTable instructorTable = new JTable();

		jScrollPane1.setViewportView(instructorTable);
		panel.add(jScrollPane1);

		WebPanel topPanel = new WebPanel();
		topPanel.setMargin(new Insets(7, 2, 7, 2));
		panel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JLabel filterLabel = new JLabel("Search: ");
		topPanel.add(filterLabel);
		WebTextField instructorFilterTF = new WebTextField();
		topPanel.add(instructorFilterTF);

		TextFilterator<Instructor> personTextFilterator = new TextFilterator<Instructor>() {
			@SuppressWarnings("unchecked")
			@Override
			public void getFilterStrings(java.util.List list, Instructor s) {
				// field you want to enable filter
				list.add(s.getId());
				list.add(s.getFirstName());
				list.add(s.getLastName());
				list.add(s.getUsername());
			}
		};

		// Table Configuration
		MatcherEditor<Instructor> textMatcherEditor = new TextComponentMatcherEditor<Instructor>(
				instructorFilterTF, personTextFilterator);
		FilterList<Instructor> filterList = new FilterList<Instructor>(
				getInstructorList(), textMatcherEditor);
		SortedList<Instructor> sortedInstructor = new SortedList<Instructor>(filterList,
				null);
		AdvancedTableModel<Instructor> model = GlazedListsSwing
				.eventTableModelWithThreadProxyList(sortedInstructor,
						new InstructorTableFormat());

		instructorSelectionModel = new EventSelectionModel<Instructor>(filterList);
		instructorTable.setSelectionModel(instructorSelectionModel);
		instructorTable.setModel(model);
		TableComparatorChooser<Instructor> tableSorter = TableComparatorChooser
				.install(instructorTable, sortedInstructor,
						TableComparatorChooser.SINGLE_COLUMN);

		StripedTableCellRenderer.installInTable(instructorTable, Color.lightGray,
				Color.white, null, null);
		instructorTable.getColumnModel().getColumn(0).setPreferredWidth(10);

		return panel;
	}

	public class InstructorTableFormat implements TableFormat<Instructor>,
			WritableTableFormat<Instructor> {

		public int getColumnCount() {
			return 4;
		}

		public String getColumnName(int column) {
			if (column == 0)
				return "ID";
			if (column == 1)
				return "First name";
			else if (column == 2)
				return "Last Name";
			else if (column == 3)
				return "Username";

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

			throw new IllegalStateException();
		}

		@Override
		public boolean isEditable(Instructor baseObject, int column) {
			return false;
		}

		@Override
		public Instructor setColumnValue(Instructor baseObject, Object editedValue,
				int column) {
			return baseObject;
		}
	}

	public EventList<com.jajeem.core.model.Instructor> getInstructorList() {
		return instructorList;
	}

	public void setInstructorList(
			EventList<com.jajeem.core.model.Instructor> instructorList) {
		this.instructorList = instructorList;
	}
}
