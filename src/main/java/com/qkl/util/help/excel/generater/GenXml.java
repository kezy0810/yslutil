package com.qkl.util.help.excel.generater;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.qkl.util.help.excel.bean.Column;
import com.qkl.util.help.excel.bean.Columns;
import com.qkl.util.help.excel.bean.ExcelConfig;
import com.qkl.util.help.excel.common.XmlConfig;

@SuppressWarnings("serial")
public class GenXml extends JFrame {
	private JPanel contentPane;
	private CardLayout card = null;
	private JPanel btnpanel;
	private JButton prevBtn;
	private JButton nextBtn;
	private JButton doneBtn;
	private JPanel pane;
	private JPanel step1;
	private JPanel step2;
	private JPanel step3;
	private JTextField classPath;
	private int step = 0;
	private JLabel label_1;
	private JTable table;
	private JButton delBtn;
	private JButton mvT;
	private JButton mvD;
	private JButton reset;
	private boolean hasRead = false;
	private JLabel label_2;
	private JLabel lblsheet;
	private JLabel label_3;
	private JLabel lblSheet;
	private JLabel label_4;
	private JCheckBox cache;
	private JTextField sheet;
	private JTextField sheetNum;
	private JTextField startRow;
	private JSlider sliderSheet;
	private JSlider sliderRow;
	private JPanel panel_1;
	private JPanel panel_2;
	private JScrollPane scrollPane_1;
	private String clasz;
	/**
	 * ???????????????xml
	 */
	private ActionListener doneListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			ExcelConfig dlExcel = new ExcelConfig();
			List<Column> columns = new ArrayList<Column>();
			dlExcel.setColumns(new Columns());
			dlExcel.getColumns().setColumns(columns);
			dlExcel.setClazz(clasz);
			dlExcel.setCache(cache.isSelected());
			dlExcel.setSheet(sheet.getText());
			dlExcel.setSheetNum(Integer.parseInt(sheetNum.getText()));
			dlExcel.setStartRow(Integer.parseInt(startRow.getText()));
			// ???
			int rows = table.getRowCount();
			Column column = null;
			for (int i = 0; i < rows; i++) {
				column = new Column();
				column.setName(String.valueOf(table.getValueAt(i, 0)));
				column.setType(String.valueOf(table.getValueAt(i, 1)));
				column.setHeader(String.valueOf(table.getValueAt(i, 2)));
				columns.add(column);
			}
			XmlConfig config = new XmlConfig();
			// filepath
			String xmlPath = filePath.getText();
			String fileName = clasz.substring(clasz.lastIndexOf('.') + 1);
			String fullPath = xmlPath + "/" + fileName + ".xml";
			try {
				config.WriteXml(dlExcel, fullPath);
				if (JOptionPane.showConfirmDialog(GenXml.this, "??????????????????????????????????",
						"??????", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(GenXml.this,
						"???????????????" + e2.getMessage());
			}
		}
	};
	private JTextField filePath;

	/**
	 * Create the frame.
	 */
	public GenXml() {
		setResizable(false);
		setTitle("XML????????????");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(Color.DARK_GRAY, 4));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		btnpanel = new JPanel();
		btnpanel.setBackground(Color.DARK_GRAY);
		btnpanel.setBorder(new LineBorder(Color.DARK_GRAY));
		btnpanel.setPreferredSize(new Dimension(10, 60));
		contentPane.add(btnpanel, BorderLayout.SOUTH);
		btnpanel.setLayout(null);

		prevBtn = new JButton("?????????");
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prev();
			}
		});
		prevBtn.setEnabled(false);
		prevBtn.setBounds(294, 10, 113, 40);
		btnpanel.add(prevBtn);

		nextBtn = new JButton("?????????");
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		nextBtn.setBounds(417, 10, 113, 40);
		btnpanel.add(nextBtn);

		doneBtn = new JButton("??????");
		doneBtn.addActionListener(doneListener);
		doneBtn.setEnabled(false);
		doneBtn.setBounds(540, 10, 113, 40);
		btnpanel.add(doneBtn);

		card = new CardLayout(0, 0);
		pane = new JPanel(card);
		contentPane.add(pane, BorderLayout.CENTER);

		step1 = new JPanel();
		step1.setBackground(Color.LIGHT_GRAY);
		step1.setBorder(new LineBorder(new Color(0, 0, 0)));
		pane.add(step1, "name_287780550285180");
		step1.setLayout(null);

		JLabel label = new JLabel("?????????????????????????????????");
		label.setFont(new Font("??????", Font.PLAIN, 18));
		label.setBounds(10, 42, 344, 43);
		step1.add(label);

		classPath = new JTextField();
		classPath.setForeground(Color.BLACK);
		classPath.setBackground(Color.WHITE);
		classPath.setFont(new Font("??????", Font.BOLD, 20));
		classPath.setBounds(10, 95, 636, 52);
		step1.add(classPath);
		classPath.setColumns(10);

		JLabel lblxml = new JLabel("??????xml????????????????????????");
		lblxml.setFont(new Font("??????", Font.PLAIN, 18));
		lblxml.setBounds(10, 218, 344, 43);
		step1.add(lblxml);

		filePath = new JTextField();
		// ????????????????????????
		String path = GenXml.class.getResource("/").getPath();
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		filePath.setText(path);
		filePath.setForeground(Color.BLACK);
		filePath.setFont(new Font("??????", Font.BOLD, 20));
		filePath.setColumns(10);
		filePath.setBackground(Color.WHITE);
		filePath.setBounds(10, 271, 636, 52);
		step1.add(filePath);

		step2 = new JPanel();
		step2.setBackground(Color.LIGHT_GRAY);
		step2.setBorder(new LineBorder(new Color(0, 0, 0)));
		pane.add(step2, "name_287788958231594");
		step2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		step2.add(scrollPane);

		table = new JTable();
		table.setRowHeight(30);
		table.setAutoCreateRowSorter(true);
		table.setDragEnabled(true);
		table.setFont(new Font("??????", Font.PLAIN, 18));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setForeground(Color.BLACK);
		table.setBackground(Color.WHITE);
		table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{
				"\u5B57\u6BB5\u540D", "\u7C7B\u578B", "\u5217\u540D"}));
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		scrollPane.setViewportView(table);

		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 40));
		step2.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(null);

		label_1 = new JLabel("????????????????????????");
		label_1.setBounds(10, 10, 157, 22);
		panel_1.add(label_1);

		panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(100, 10));
		step2.add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(null);

		reset = new JButton("??????");
		reset.setBounds(3, 0, 93, 25);
		panel_2.add(reset);

		mvT = new JButton("??????");
		mvT.setBounds(3, 30, 93, 25);
		panel_2.add(mvT);

		mvD = new JButton("??????");
		mvD.setBounds(3, 60, 93, 25);
		panel_2.add(mvD);

		delBtn = new JButton("??????");
		delBtn.setBounds(3, 90, 93, 25);
		panel_2.add(delBtn);
		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ???????????????
				DefaultTableModel tableModel = (DefaultTableModel) table
						.getModel();
				int row = table.getSelectedRow();
				if (row > -1) {
					tableModel.removeRow(row);
					if (row < table.getRowCount()) {
						table.getSelectionModel()
								.setSelectionInterval(row, row);
					} else if (row > 1) {
						table.getSelectionModel()
								.setSelectionInterval(row, row);
					}
				}
			}
		});
		mvD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ???????????????
				DefaultTableModel tableModel = (DefaultTableModel) table
						.getModel();
				int row = table.getSelectedRow();
				if (row < table.getRowCount()) {
					tableModel.moveRow(row, row, ++row);
					table.getSelectionModel().setSelectionInterval(row, row);
				}
			}
		});
		mvT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ???????????????
				DefaultTableModel tableModel = (DefaultTableModel) table
						.getModel();
				int row = table.getSelectedRow();
				if (row > 0) {
					tableModel.moveRow(row, row, --row);
					table.getSelectionModel().setSelectionInterval(row, row);
				}
			}
		});
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReadInTable(classPath.getText());
			}
		});

		step3 = new JPanel();
		step3.setBackground(Color.LIGHT_GRAY);
		step3.setBorder(new LineBorder(new Color(0, 0, 0)));
		pane.add(step3, "name_287798491067114");
		step3.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		step3.add(panel);
		panel.setLayout(null);

		lblsheet = new JLabel("sheet?????????");
		lblsheet.setBounds(106, 226, 99, 15);
		panel.add(lblsheet);

		label_3 = new JLabel("???????????????");
		label_3.setBounds(106, 291, 99, 24);
		panel.add(label_3);

		lblSheet = new JLabel("sheet?????????");
		lblSheet.setBounds(106, 261, 99, 15);
		panel.add(lblSheet);

		label_4 = new JLabel("???????????????");
		label_4.setBounds(106, 191, 99, 15);
		panel.add(label_4);

		cache = new JCheckBox("???????????????????????????????????????");
		cache.setSelected(true);
		cache.setBounds(215, 184, 312, 30);
		panel.add(cache);

		sheet = new JTextField();
		sheet.setText("Sheet0");
		sheet.setColumns(10);
		sheet.setBounds(215, 254, 312, 30);
		panel.add(sheet);

		sheetNum = new JTextField();
		sheetNum.setHorizontalAlignment(SwingConstants.CENTER);
		sheetNum.setBackground(Color.DARK_GRAY);
		sheetNum.setForeground(Color.WHITE);
		sheetNum.setEnabled(false);
		sheetNum.setBounds(461, 219, 66, 30);
		panel.add(sheetNum);
		sheetNum.setColumns(10);

		startRow = new JTextField();
		startRow.setHorizontalAlignment(SwingConstants.CENTER);
		startRow.setForeground(Color.WHITE);
		startRow.setBackground(Color.DARK_GRAY);
		startRow.setEnabled(false);
		startRow.setColumns(10);
		startRow.setBounds(461, 289, 66, 30);
		panel.add(startRow);

		sliderRow = new JSlider();
		sliderRow.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				startRow.setText("" + sliderRow.getValue());
			}
		});
		sliderRow.setMaximum(10);
		sliderRow.setValue(0);
		sliderRow.setBounds(215, 289, 240, 30);
		panel.add(sliderRow);

		sliderSheet = new JSlider();
		sliderSheet.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sheetNum.setText("" + sliderSheet.getValue());
			}
		});
		sliderSheet.setValue(0);
		sliderSheet.setMaximum(10);
		sliderSheet.setBounds(215, 219, 240, 30);
		panel.add(sliderSheet);

		label_2 = new JLabel("           ???????????????");
		label_2.setPreferredSize(new Dimension(60, 35));
		step3.add(label_2, BorderLayout.NORTH);
	}

	/**
	 * ??????xml?????????
	 */
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenXml frame = new GenXml();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// ??????
	private void next() {
		switch (step) {
			case 0 :
				next0();
				break;
			case 1 :
				next1();
				break;
			case 2 :
				done();
				break;
			default :
				break;
		}
	}

	// ??????
	private void prev() {
		switch (step) {
			case 1 :
				prev0();
				break;
			case 2 :
				prev1();
				break;
			default :
				break;
		}
	}

	private void next0() {
		prevBtn.setEnabled(true);
		String clasz = classPath.getText();
		String xmlPath = filePath.getText();
		if (!clasz.equals("") && !xmlPath.equals("")) {
			try {
				Class.forName(clasz).newInstance();
				card.next(pane);
				prevBtn.setEnabled(true);
				if (!hasRead || !this.clasz.equals(clasz)) {
					ReadInTable(clasz);
				}
				step++;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(GenXml.this, "????????????????????????????????????!");
			}
		} else {
			JOptionPane.showMessageDialog(GenXml.this, "???????????????!");
		}
	}

	private void next1() {
		// ??????xml????????????
		card.next(pane);
		nextBtn.setEnabled(false);
		doneBtn.setEnabled(true);
		step++;
	}

	private void done() {

	}

	private void prev0() {
		prevBtn.setEnabled(false);
		doneBtn.setEnabled(false);
		card.previous(pane);
		step--;
	}

	private void prev1() {
		prevBtn.setEnabled(true);
		nextBtn.setEnabled(true);
		doneBtn.setEnabled(false);
		card.previous(pane);
		step--;
	}

	// ?????????????????????
	private void ReadInTable(String clasz) {
		this.clasz = clasz;
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		while (tableModel.getRowCount() > 0) {
			tableModel.removeRow(0);
		}
		try {
			BeanInfo sourceBean = Introspector.getBeanInfo(
					Class.forName(clasz), Object.class);
			PropertyDescriptor[] ps = sourceBean.getPropertyDescriptors();
			for (int i = 0; i < ps.length; i++) {
				if (ps[i].getPropertyType().equals(Class.class)) {
					continue;
				}
				tableModel.addRow(new Object[]{ps[i].getName(),
						ps[i].getPropertyType().getName(), ps[i].getName()});
			}
			hasRead = true;
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(GenXml.this, "????????????:" + e.getMessage());
		}
	}
}
