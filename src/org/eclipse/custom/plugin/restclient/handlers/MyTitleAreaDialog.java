package org.eclipse.custom.plugin.restclient.handlers;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class MyTitleAreaDialog extends TitleAreaDialog {

	private String selectedValue;
	private static String DIALOG_TITLE = "Rest Client For Rest APIs";

	public MyTitleAreaDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		parent.getShell().setText(DIALOG_TITLE);

		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		container.setLayout(layout);

		container.setFont(parent.getFont());

		createDropDown(container);

		return area;
	}

	private void createDropDown(Composite container) {
		Label label = new Label(container, SWT.NONE);
		label.setText("Select API ");

		GridData dataFirstName = new GridData(GridData.FILL_HORIZONTAL);
		dataFirstName.grabExcessHorizontalSpace = false;

		label.setLayoutData(dataFirstName);

		Combo combo = new Combo(container, SWT.VERTICAL | SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);

		combo.add("Current Time", 0);
		combo.add("Service Uptime", 1);

		// User selected an item in the Combo.
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int idx = combo.getSelectionIndex();
				String selected = combo.getItem(idx);
				selectedValue = selected;
			}
		});

	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

}
