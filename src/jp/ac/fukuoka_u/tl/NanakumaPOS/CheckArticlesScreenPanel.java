//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  商品チェック画面を実現するクラス。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

public class CheckArticlesScreenPanel extends JPanel implements ActionListener, TableModelListener {
	/* 商品チェック画面の状態 */
	enum CheckArticlesScreenPanelState {
		Initialized,		// 初期化済み
		CheckingArticles,	// 商品チェック中
		PaymentFinished,	// 決済済み
	};

	/* 関連先 */
	// POS端末全体制御
	private POSTerminalApp app;

	/* 内部データ */
	// 商品チェック画面のフレーム
	private JFrame frame;
	// 商品チェック画面の状態
	private CheckArticlesScreenPanelState state;
	// フォーカスされている商品販売の番号
	private int salesIndexInFocus;

	/* ウィジェット */
	// スクロールペイン
	private JScrollPane scroll;
	// 販売商品表
	private JTable articlesTable;
	// 会員番号入力ボタン
	private JButton enterMembershipIDButton;
	// 販売単価変更ボタン
	private JButton changeSalesPriceButton;
	// 販売個数変更ボタン
	private JButton changeQuantityButton;
	// 決済ボタン
	private JButton paymentButton;
	// ホームボタン
	private JButton homeButton;
	// 商品コードラベル
	private JLabel articleCodeLabel;
	// 商品コード入力欄
	private JTextField articleCodeField;
	// 会員番号ラベル
	private JLabel memberIDLabel;
	// 会員番号欄
	private JTextField memberIDField;
	// 会員氏名ラベル
	private JLabel memberNameLabel;
	// 会員氏名欄
	private JTextField memberNameField;
	// 商品コード入力ボタン
	private JButton articleCodeButton;
	// 合計金額ラベル
	private JLabel totalPriceLabel;
	// 合計金額欄
	private JTextField totalPriceField;
	// お預かりラベル
	private JLabel paidPriceLabel;
	// お預かり金額欄
	private JTextField paidPriceField;
	// おつりラベル
	private JLabel changePriceLabel;
	// おつり金額欄
	private JTextField changePriceField;

	/*
	 * コンストラクタ。商品チェック画面が保有するオブジェクトを生成する。
	 */
	public CheckArticlesScreenPanel(POSTerminalApp _app) {
		// 関連先を初期化する。
		app = _app;

		// 内部データを初期化する。
		frame = (JFrame)SwingUtilities.getRoot(this);
		state = CheckArticlesScreenPanelState.Initialized;
		salesIndexInFocus = -1;

		// 自身を初期化する。
		setLayout(null);

		// 販売商品一覧画面を生成する。
		DefaultTableCellRenderer rightAlignmentRenderer = new DefaultTableCellRenderer();
		rightAlignmentRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		articlesTable = new JTable(app.getSalesUnderChecking());
		app.getSalesUnderChecking().addTableModelListener(this);
		articlesTable.setEnabled(false);
		articlesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		articlesTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		articlesTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		articlesTable.getColumnModel().getColumn(2).setPreferredWidth(300);
		articlesTable.getColumnModel().getColumn(3).setPreferredWidth(100);
		articlesTable.getColumnModel().getColumn(3).setCellRenderer(rightAlignmentRenderer);
		articlesTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		articlesTable.getColumnModel().getColumn(4).setCellRenderer(rightAlignmentRenderer);
		articlesTable.getColumnModel().getColumn(5).setPreferredWidth(100);
		articlesTable.getColumnModel().getColumn(5).setCellRenderer(rightAlignmentRenderer);
		scroll = new JScrollPane(articlesTable);
		scroll.setBounds(16, 16, 800, 524);
		add(scroll);

		// 会員番号入力ボタンを生成する。
		enterMembershipIDButton = new JButton("会員番号入力");
		enterMembershipIDButton.setBounds(832, 16, 160, 48);
		enterMembershipIDButton.addActionListener(this);
		enterMembershipIDButton.setActionCommand("enterMembershipID");
		add(enterMembershipIDButton);

		// 販売単価変更ボタンを生成する。
		changeSalesPriceButton = new JButton("販売単価変更");
		changeSalesPriceButton.setBounds(832, 80, 160, 48);
		changeSalesPriceButton.addActionListener(this);
		changeSalesPriceButton.setActionCommand("changeSalesPrice");
		add(changeSalesPriceButton);

		// 販売個数変更ボタンを生成する。
		changeQuantityButton = new JButton("販売個数変更");
		changeQuantityButton.setBounds(832, 144, 160, 48);
		changeQuantityButton.addActionListener(this);
		changeQuantityButton.setActionCommand("changeQuantity");
		add(changeQuantityButton);

		// 決済ボタンを生成する。
		paymentButton = new JButton("決済");
		paymentButton.setBounds(832, 208, 160, 48);
		paymentButton.addActionListener(this);
		paymentButton.setActionCommand("payment");
		add(paymentButton);

		// ホームボタンを生成する。
		homeButton = new JButton("ホーム画面");
		homeButton.setBounds(832, 272, 160, 48);
		homeButton.addActionListener(this);
		homeButton.setActionCommand("home");
		add(homeButton);

		// 商品コード入力欄を生成する。
		articleCodeLabel = new JLabel("商品コード入力");
		articleCodeLabel.setBounds(16, 568, 100, 24);
		add(articleCodeLabel);
		articleCodeField = new JTextField(8);
		articleCodeField.setBounds(116, 568, 100, 24);
		articleCodeField.setBackground(Color.YELLOW);
		articleCodeField.setHorizontalAlignment(JTextField.LEFT);
		articleCodeField.setEditable(true);
		add(articleCodeField);

		// 商品コード入力ボタンを生成する。
		articleCodeButton = new JButton("入力");
		articleCodeButton.setBounds(226, 568, 80, 24);
		articleCodeButton.addActionListener(this);
		articleCodeButton.setActionCommand("articleCode");
		add(articleCodeButton);

		// 会員番号欄を生成する。
		memberIDLabel = new JLabel("会員番号");
		memberIDLabel.setBounds(396, 568, 100, 24);
		add(memberIDLabel);
		memberIDField = new JTextField(8);
		memberIDField.setBounds(496, 568, 100, 24);
		memberIDField.setBackground(Color.CYAN);
		memberIDField.setEditable(false);
		add(memberIDField);

		// 会員氏名欄を生成する。
		memberNameLabel = new JLabel("会員氏名");
		memberNameLabel.setBounds(396, 600, 100, 24);
		add(memberNameLabel);
		memberNameField = new JTextField(32);
		memberNameField.setBounds(496, 600, 100, 24);
		memberNameField.setBackground(Color.CYAN);
		memberNameField.setEditable(false);
		add(memberNameField);

		// お預かり欄を生成する。
		paidPriceLabel = new JLabel("お預かり");
		paidPriceLabel.setBounds(616, 568, 100, 24);
		add(paidPriceLabel);
		paidPriceField = new JTextField(8);
		paidPriceField.setBounds(716, 568, 100, 24);
		paidPriceField.setBackground(Color.CYAN);
		paidPriceField.setHorizontalAlignment(JTextField.RIGHT);
		paidPriceField.setEditable(false);
		add(paidPriceField);

		// 合計金額欄を生成する。
		totalPriceLabel = new JLabel("合計金額");
		totalPriceLabel.setBounds(616, 600, 100, 24);
		add(totalPriceLabel);
		totalPriceField = new JTextField(8);
		totalPriceField.setBounds(716, 600, 100, 24);
		totalPriceField.setBackground(Color.CYAN);
		totalPriceField.setHorizontalAlignment(JTextField.RIGHT);
		totalPriceField.setEditable(false);
		add(totalPriceField);

		// おつり欄を生成する。
		changePriceLabel = new JLabel("おつり");
		changePriceLabel.setBounds(616, 632, 100, 24);
		add(changePriceLabel);
		changePriceField = new JTextField(8);
		changePriceField.setBounds(716, 632, 100, 24);
		changePriceField.setBackground(Color.CYAN);
		changePriceField.setHorizontalAlignment(JTextField.RIGHT);
		changePriceField.setEditable(false);
		add(changePriceField);

		// 内部データとウィジェットを初期化する。
		setState(CheckArticlesScreenPanelState.Initialized);
	}

	/*
	 * 商品チェック画面の状態を変更する。
	 */
	public void setState(CheckArticlesScreenPanelState _state) {
		state = _state;
		switch (state) {
		case Initialized:
			articleCodeField.setText("");
			articleCodeField.setEnabled(true);
			articleCodeButton.setEnabled(true);
			memberIDField.setText("");
			memberNameField.setText("");
			paidPriceField.setText("");
			totalPriceField.setText("0");
			changePriceField.setText("");
			enterMembershipIDButton.setEnabled(true);
			changeSalesPriceButton.setEnabled(false);
			changeQuantityButton.setEnabled(false);
			paymentButton.setEnabled(false);
			articleCodeField.requestFocusInWindow();
			break;
		case CheckingArticles:
			articleCodeButton.setEnabled(true);
			enterMembershipIDButton.setEnabled(true);
			//changeSalesPriceButton.setEnabled(false);
			//changeQuantityButton.setEnabled(false);
			paymentButton.setEnabled(true);
			break;
		case PaymentFinished:
			articlesTable.clearSelection();
			articleCodeField.setEnabled(false);
			articleCodeButton.setEnabled(false);
			enterMembershipIDButton.setEnabled(false);
			changeSalesPriceButton.setEnabled(false);
			changeQuantityButton.setEnabled(false);
			paymentButton.setEnabled(false);
			break;
		}
	}

	/*
	 * 商品コード入力欄をクリアする。
	 */
	public void clearArticleCodeField() {
		articleCodeField.setText("");
		articleCodeField.requestFocusInWindow();
	}

	/*
	 * お預かり欄に金額を表示する。
	 */
	public void setPaidPrice(int paidPrice) {
		paidPriceField.setText(Integer.toString(paidPrice));
	}

	/*
	 * おつり欄に金額を表示する。
	 */
	public void setChangePrice(int changePrice) {
		changePriceField.setText(Integer.toString(changePrice));
	}

	/*
	 * 商品販売表上の idx 番目の商品販売にフォーカスをあてる。
	 */
	public void focusSales(int idx) {
		// 商品チェック画面が商品チェック中状態の場合のみ処理する。
		if (state == CheckArticlesScreenPanelState.CheckingArticles) {
			// 商品販売表上の当該商品販売にフォーカスをあてる。
			salesIndexInFocus = idx;
			articlesTable.setRowSelectionInterval(idx, idx);
			// 当該商品販売の販売単価と販売個数を変更できるように販売単価変更ボ
			// タンと販売個数変更ボタンを有効にする。
			changeSalesPriceButton.setEnabled(true);
			changeQuantityButton.setEnabled(true);
		}
	}

	/*
	 * 商品販売表上の商品販売のフォーカスをはずす。
	 */
	public void unfocusSales() {
		// 商品販売表上のすべての商品販売のフォーカスをはずす。
		articlesTable.clearSelection();
		// 販売単価変更ボタンと販売個数変更ボタンを無効にする。
		changeSalesPriceButton.setEnabled(false);
		changeQuantityButton.setEnabled(false);
	}

	/*
	 * 商品コードが入力されたときに呼び出される。
	 */
	private void articleCodeEntered() {
		String articleCode = articleCodeField.getText();
		app.articleCodeEntered(articleCode);
		articleCodeField.requestFocusInWindow();
	}

	/*
	 * 会員番号の入力が要求されたときに呼び出される。
	 */
	private void memberIDEnteringRequested() {
		// 店員に会員番号の入力を求める。
		String memrberID = JOptionPane.showInputDialog(frame, "会員番号を入力してください。");
		app.memberIDEntered(memrberID);
		articleCodeField.requestFocusInWindow();
	}

	/*
	 * 販売単価の変更が要求されたときに呼び出される。
	 */
	private void changeSalesPriceRequested() {
		// フォーカスされている商品販売の販売単価を取得する。
		int salesPrice = app.getSalesUnderChecking().getIthSale(salesIndexInFocus).getSalesPrice();

		// 店員に販売単価の入力を求める。
		String str = JOptionPane.showInputDialog(frame, "販売単価を入力してください。", salesPrice);
		if (str != null) {
			// 入力があった場合，
			try {
				// 入力内容（文字列）を販売単価（整数）に変換する。
				salesPrice = Integer.parseInt(str);
				// 販売単価が0未満の場合は例外を投げる。
				if (salesPrice < 0) {
					throw new NumberFormatException();
				}
				// 販売単価の変更を確定する。
				app.changeSalesPriceRequested(salesIndexInFocus, salesPrice);
			}
			// 入力はあってもその書式が不正の場合，
			catch (NumberFormatException ex) {
				// 店員にエラーを通知する。
				JOptionPane.showMessageDialog(frame, "販売単価の入力が不正です。", "エラー", JOptionPane.ERROR_MESSAGE);
			}
		}
		// 商品コード入力欄にフォーカスをあてる。
		articleCodeField.requestFocusInWindow();
	}

	/*
	 * 販売個数の変更が要求されたときに呼び出される。
	 */
	private void changeSalesQuantityRequested() {
		// フォーカスされている商品販売の販売個数を取得する。
		int salesQuantity = app.getSalesUnderChecking().getIthSale(salesIndexInFocus).getSalesQuantity();

		// 店員に販売個数の入力を求める。
		String str = JOptionPane.showInputDialog(frame, "販売個数を入力してください。", salesQuantity);
		if (str != null) {
			// 入力があった場合，
			try {
				// 入力内容（文字列）を販売個数（整数）に変換する。
				salesQuantity = Integer.parseInt(str);
				// 販売個数が0未満の場合は例外を投げる。
				if (salesQuantity < 0) {
					throw new NumberFormatException();
				}
				// 販売個数の変更を確定する。
				app.changeSalesQuantityRequested(salesIndexInFocus, salesQuantity);
			}
			// 入力はあってもその書式が不正の場合，
			catch (NumberFormatException ex) {
				// 店員にエラーを通知する。
				JOptionPane.showMessageDialog(frame, "販売個数の入力が不正です。", "エラー", JOptionPane.ERROR_MESSAGE);
			}
		}
		// 商品コード入力欄にフォーカスをあてる。
		articleCodeField.requestFocusInWindow();
	}

	/*
	 * 決済が要求されたときに呼び出される。
	 */
	private void paymentRequested() {
		if (app.paymentRequested()) {
			// 決済が完了した場合はホームボタンにフォーカスをあてる。
			homeButton.requestFocusInWindow();
		} else {
			// 決済が未完了の場合は商品コード入力欄にフォーカスをあてる。
			articleCodeField.requestFocusInWindow();
		}
	}

	/*
	 * 商品チェックのキャンセルが要求されたときに呼び出される。
	 */
	private void checkArticlesCancelled() {
		switch (state) {
		case Initialized:
		case PaymentFinished:
			// 商品チェック画面が初期化済み状態の場合，商品は何もチェックされて
			// いない状態なので問答無用でキャンセルして構わない。
			// 商品チェック画面が決済済みの場合，商品はすべてチェックされて決済
			// が完了している状態なので問答無用でキャンセルして構わない。
			app.checkArticlesCancelled();
			break;
		case CheckingArticles:
			// 商品チェック画面が商品チェック中状態の場合，キャンセルするとそれ
			// までの入力が無駄になるので，店員に確認が必要である。
			if (JOptionPane.showConfirmDialog(frame, "商品のチェックをキャンセルしますか？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				// キャンセル応諾の場合，商品チェックをキャンセルする。
				app.checkArticlesCancelled();
			} else {
				// キャンセル拒否の場合，商品コード入力欄にフォーカスをあてる。
				articleCodeField.requestFocusInWindow();
			}
			break;
		}
	}

	/*
	 * 商品チェック画面上のボタンが押されるときに呼び出される。
	 */
	@Override
	public void actionPerformed (ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("articleCode")) {
			// 商品コード入力ボタンが押下されたときは商品コード入力処理を呼び出
			// す。
			articleCodeEntered();
		} else if (cmd.equals("enterMembershipID")) {
			// 会員番号入力ボタンが押下されたときは会員番号入力処理を呼び出す。
			memberIDEnteringRequested();
		} else if (cmd.equals("changeSalesPrice")) {
			// 商品単価変更ボタンが押下されたときは商品単価変更処理を呼び出す。
			changeSalesPriceRequested();
		} else if (cmd.equals("changeQuantity")) {
			// 商品個数変更ボタンが押下されたときは商品個数変更処理を呼び出す。
			changeSalesQuantityRequested();
		} else if (cmd.equals("payment")) {
			// 決済ボタンが押下されたときは決済処理を呼び出す。
			paymentRequested();
		} else if (cmd.equals("home")) {
			// ホーム画面ボタンが押下されたときは商品チェックキャンセル処理を呼
			// び出す。
			checkArticlesCancelled();
		}
	}

	/*
	 * 決済対象商品販売リストが更新されたら呼び出される。
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		// 合計金額欄の表示を更新する。
		totalPriceField.setText(Integer.toString(app.getSalesUnderChecking().getTotalPrice()));
	}

	/*
	 *
	 */
	public void memberUnderCheckingChanged() {
		// 会員表示欄の表示を更新する。
		Member member = app.getMemberUnderChecking();
		if (member != null) {
			memberIDField.setText(member.getID());
			memberNameField.setText(member.getName());
		}
	}
}
