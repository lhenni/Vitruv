package tools.vitruv.extensions.changevisualization.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.ChangeVisualization;

/**
 * A CdsTable displays all different ChangeDataSets of a given ChangesTab in a JTable
 * 
 * @author Andreas Loeffler
 */
public class CdsTable extends JPanel implements MouseWheelListener{

	/**
	 * Needed for eclipse to stop warning about serialVersionIds. This feature will never been used. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Table implementing the actual cds visualization
	 */
	private JTable table;

	private List<String> highlightedCdsIds;

	/**
	 * Constructs a new CdsTable
	 */
	public CdsTable() {
		super(new BorderLayout());	
		createUI();
	}

	/**
	 * Creates the ui of the CdsTable
	 */
	private void createUI() {

		createTable();

		table.setModel(createModel());

		//Update calumn widths, has to be done after adding a model
		table.getColumnModel().getColumn( 0 ).setPreferredWidth( 300 );//ID
		table.getColumnModel().getColumn( 1 ).setPreferredWidth( 70 );//Time
		table.getColumnModel().getColumn( 2 ).setPreferredWidth( 70 );//propagated changes
		table.getColumnModel().getColumn( 3 ).setPreferredWidth( 70 );//propagated changes
		table.getColumnModel().getColumn( 4 ).setPreferredWidth( 70 );//propagated changes
		table.getColumnModel().getColumn( 5 ).setPreferredWidth( 70 );//original changes
		table.getColumnModel().getColumn( 6 ).setPreferredWidth( 70 );//consequential changes

		//Add the table to a scrollpane
		JScrollPane scroller=new JScrollPane(table);
		add(scroller,BorderLayout.CENTER);

		//Listen to the scroller to implement zooming
		scroller.addMouseWheelListener(this);		
	}

	/**
	 * Creates the table model
	 * 
	 * @return The table model
	 */
	private TableModel createModel() {

		//Create the column namens
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("ID");
		columnNames.add("Time");
		columnNames.add("Source Model");
		columnNames.add("Target Model");
		columnNames.add("Propagated changes");
		columnNames.add("Original changes");
		columnNames.add("Consequential changes");

		//Create the data vector
		Vector<Vector<?>> rowData=new Vector<Vector<?>>();	
		return new DefaultTableModel(rowData, columnNames){	
			/**
			 * Needed for eclipse to stop warning about serialVersionIds. This feature will never been used. 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false; //Table is not editable
			}	
			//JTable uses this method to determine the default renderer editor for each cell
			public Class<?> getColumnClass(int c) {
				switch(c){
				case 4:
				case 5:
				case 6:
					return Integer.class;
				case 1:
					return Date.class;
				default:
					return String.class;
				}
			}
		};
	}

	/**
	 * Creates the table and sets its default behaviour
	 */
	private void createTable() {
		table = new JTable();

		table.setAutoCreateRowSorter(true);
		table.setRowHeight(24);
		table.setShowGrid(true);
		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.setFont(ChangeVisualizationUI.DEFAULT_TABLE_FONT);
		table.getTableHeader().setFont(ChangeVisualizationUI.DEFAULT_TABLE_HEADER_FONT);

		//Sets a new Default Renderer for Date-Object
		table.setDefaultRenderer(Date.class,new DefaultTableCellRenderer(){			
			private static final long serialVersionUID = 1L;

			private final SimpleDateFormat df=new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss");			
			protected void setValue(Object value) {
				setText((value == null) ? "" : df.format((Date)value));
			}
		});
		
		table.setDefaultRenderer(String.class,new DefaultTableCellRenderer(){			
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
				//Reset font color
				setForeground(table.getForeground());				
										
				//Get default visualization
				Component comp=super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				//Just as an info: comp==this
				
				//if the String is highlighted, set the color after super.getTableCell...
				//to overwrite any potential coloring of the superclasses implementation
				if(column==0&&shouldHighlight((String)value)) {
					comp.setForeground(ChangesTab.HIGHLIGHT_COLOR);
				}
				
				return comp;
			}			
		});
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {	
		//Implements the usual strg + mousewheel behaviour for zooming
		if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == 0) return;
		if(e.getWheelRotation()<=-1) {
			float newSize=table.getFont().getSize()+2;
			if(newSize>30) newSize=30;
			table.setFont(table.getFont().deriveFont(newSize));
			table.setRowHeight((int)(newSize+10));
		}else if(e.getWheelRotation()>=1) {
			float newSize=table.getFont().getSize()-2;
			if(newSize<5) newSize=5;
			table.setFont(table.getFont().deriveFont(newSize));
			table.setRowHeight((int)(newSize+10));
		}
	}

	/**
	 * Selects a given row of the table
	 * 
	 * @param row The row to select
	 */
	public void setSelected(int row) {
		table.getSelectionModel().setSelectionInterval(row, row);
	}	

	/**
	 * Adds a given ListSelectionListener to this table
	 * @param listener The listener to add
	 */
	public void addListSelectionListener(ListSelectionListener listener) {
		table.getSelectionModel().addListSelectionListener(listener);
	}

	/**
	 * Appends a ChangeDataSet to this cdsTable
	 * @param cds The cds to append
	 */
	public void appendCds(ChangeDataSet cds) {
		Vector<Object> line=encode(cds);
		((DefaultTableModel)table.getModel()).addRow(line);	
	}

	/**
	 * Creates a Vector to display in the table that shows the relevant information
	 * of a given cds
	 * 
	 * @param cds The cds to process
	 * @return Vector suitable for usage in a JTable
	 */
	private Vector<Object> encode(ChangeDataSet cds) {
		Vector<Object> line=new Vector<Object>();
		line.add(cds.getCdsID());		
		line.add(cds.getCreationTime());
		line.add(cds.getSourceModelInfo());
		line.add(cds.getTargetModelInfo());
		line.add(cds.getNrPChanges());
		line.add(cds.getNrOChanges());
		line.add(cds.getNrCChanges());
		return line;
	}

	/**
	 * Gets the selected row of this table. -1 if none is selected
	 * @return The selected row
	 */
	public int getSelectedRow() {
		return table.getSelectedRow();
	}
	
	public synchronized void setHighlightedCdsIDs(List<String> highlightedCdsIds) {
		this.highlightedCdsIds=highlightedCdsIds;
	}
	
	private synchronized boolean shouldHighlight(String value) {
		if(highlightedCdsIds==null||highlightedCdsIds.isEmpty()) return false;
		return highlightedCdsIds.contains(value);
	}

}
