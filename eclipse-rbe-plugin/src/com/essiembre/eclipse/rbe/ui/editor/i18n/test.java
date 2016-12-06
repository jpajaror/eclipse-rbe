package com.essiembre.eclipse.rbe.ui.editor.i18n;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;

import com.essiembre.eclipse.rbe.RBEPlugin;
import com.essiembre.eclipse.rbe.ui.UIUtils;

public class test extends Composite {

    private final Font boldFont;
    private final Font smallFont;

    private ITextViewer textViewer, textComment;

    private Button commentedCheckbox;
    private Button gotoButton;
    private Button duplButton;
    private Button simButton;

    /*default*/ String activeKey;
 private Composite composite;

    /**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public test(Composite parent, int style) {
		super(parent, style);
        this.boldFont = UIUtils.createFont(this, SWT.BOLD, 0);
        this.smallFont = UIUtils.createFont(SWT.NONE, -1);

        GridLayout gridLayout = new GridLayout(1, false);        
        gridLayout.horizontalSpacing = 0;
        gridLayout.verticalSpacing = 2;
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;

        createLabelRow();
        createTextViewerRow();

        setLayout(gridLayout);
        GridData gd = new GridData(GridData.FILL_BOTH);
        setLayoutData(gd);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

    /**
     * Creates the text row.
     */
    private void createTextViewerRow() {
        
        composite = new Composite(this, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.horizontalSpacing = 5;
        gridLayout.verticalSpacing = 0;
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        composite.setLayout(gridLayout);
        composite.setLayoutData(
                new GridData(GridData.FILL_HORIZONTAL));

//       int vscroll = RBEPreferences.getAutoAdjust() ? 0 : SWT.V_SCROLL;
        textViewer = new TextViewer(composite, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL 
                | SWT.V_SCROLL | SWT.BORDER);

        GridData gridData = new GridData();

        // Similar button
        gridData.horizontalAlignment = GridData.END;
        gridData.grabExcessHorizontalSpace = true;
        textComment = new TextViewer(composite, SWT.MULTI | SWT.WRAP | SWT.H_SCROLL 
                | SWT.V_SCROLL | SWT.BORDER);

        textViewer.setDocument(new Document());
        textComment.setDocument(new Document());

        textViewer.activatePlugins();
        textComment.activatePlugins();

        final StyledText textBox = textViewer.getTextWidget();
        
        textBox.setEnabled(false);
        // Addition by Eric FETTWEIS
        // Note that this does not seem to work... It would however be useful
        // for arabic and some other languages  
//        textBox.setOrientation(getOrientation(locale));
        
        FontRegistry fontRegistry = PlatformUI.getWorkbench().getThemeManager()
                .getCurrentTheme().getFontRegistry();
        Font font = fontRegistry.get(
                "com.essiembre.eclipse.rbe.ui.preferences.fontDefinition");
        if ( font != null ) {
           textBox.setFont(font);
        }
        
        gridData = new GridData();
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalAlignment = GridData.BEGINNING;
//        gridData.grabExcessHorizontalSpace = true;
//        gridData.heightHint = UIUtils.getHeightInChars(textBox, 3);
        textBox.setLayoutData(gridData);

        GridData gridData2 = new GridData();
        gridData2.verticalAlignment = GridData.FILL;
        gridData2.grabExcessVerticalSpace = true;
        gridData2.horizontalAlignment = GridData.END;
        textComment.getTextWidget().setLayoutData(gridData2);

    }

    /**
     * Creates the text field label, icon, and commented check box.
     */
    private void createLabelRow() {
        Composite labelComposite = new Composite(this, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 6;
        gridLayout.horizontalSpacing = 5;
        gridLayout.verticalSpacing = 0;
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        labelComposite.setLayout(gridLayout);
        labelComposite.setLayoutData(
                new GridData(GridData.FILL_HORIZONTAL));

        // Locale text
        Label txtLabel = new Label(labelComposite, SWT.NONE);
        txtLabel.setText(" " + 
                "locale" + " ");
        txtLabel.setFont(boldFont);
        GridData gridData = new GridData();

        // Similar button
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.END;
        gridData.grabExcessHorizontalSpace = true;
        simButton = new Button(labelComposite, SWT.PUSH | SWT.FLAT);
        simButton.setImage(UIUtils.getImage("similar.gif"));
        simButton.setLayoutData(gridData);
        simButton.setVisible(false);
        simButton.setToolTipText(
                RBEPlugin.getString("value.similar.tooltip"));
        simButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                String head = RBEPlugin.getString(
                        "dialog.similar.head");
                String body = RBEPlugin.getString(
                        "dialog.similar.body", activeKey,
                        "locale");
//                body += "\n\n";
//                for (Iterator<BundleEntry> iter = 
//                        similarVisitor.getSimilars().iterator();
//                iter.hasNext();) {
//                    body += "        " + iter.next().getKey() + "\n";
//                }
                MessageDialog.openInformation(getShell(), head, body); 
            }
        });

        // Duplicate button
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.END;
        duplButton = new Button(labelComposite, SWT.PUSH | SWT.FLAT);
        duplButton.setImage(UIUtils.getImage("duplicate.gif"));
        duplButton.setLayoutData(gridData);
        duplButton.setVisible(false);
        duplButton.setToolTipText(
                RBEPlugin.getString("value.duplicate.tooltip"));

        duplButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                String head = RBEPlugin.getString(
                        "dialog.identical.head");
                String body = RBEPlugin.getString(
                        "dialog.identical.body", activeKey,
                        "locale");
                body += "\n\n";
//                for (Iterator<BundleEntry> iter = 
//                        duplVisitor.getDuplicates().iterator();
//                iter.hasNext();) {
//                    body += "        " + iter.next().getKey() + "\n";
//                }
                MessageDialog.openInformation(getShell(), head, body); 
            }
        });

        // Commented checkbox
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.END;
        //gridData.grabExcessHorizontalSpace = true;
        commentedCheckbox = new Button(
                labelComposite, SWT.CHECK);
        commentedCheckbox.setText("#");
        commentedCheckbox.setFont(smallFont);
        commentedCheckbox.setLayoutData(gridData);
        commentedCheckbox.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
//                resetCommented();
//                updateBundleOnChanges();
            }
        });
        commentedCheckbox.setEnabled(false);

        // Country flag
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.END;
        Label imgLabel = new Label(labelComposite, SWT.NONE);
        imgLabel.setLayoutData(gridData);
        imgLabel.setImage(UIUtils.getImage("countries/us.gif"));

        // Goto button
        gridData = new GridData();
        gridData.horizontalAlignment = GridData.END;
        gotoButton = new Button(
                labelComposite, SWT.ARROW | SWT.RIGHT);
        gotoButton.setToolTipText(
                RBEPlugin.getString("value.goto.tooltip"));
        gotoButton.setEnabled(false);
        gotoButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
//                ITextEditor editor = resourceManager.getSourceEditor(
//                        locale).getEditor();
//                Object activeEditor = 
//                    editor.getSite().getPage().getActiveEditor();
//                if (activeEditor instanceof ResourceBundleEditor) {
//                    ((ResourceBundleEditor) activeEditor).setActivePage(locale);
//                }
            }
        });
        gotoButton.setLayoutData(gridData);
    }
}
