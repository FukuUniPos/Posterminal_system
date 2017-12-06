//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  「決済」ダイアログ。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PaymentDialog extends JDialog implements ActionListener{
	/* データ */
	// 合計金額
	private int totalPrice;
	// お預かり
	private int paidPrice;
	// OKボタンが押されたか
	private Boolean confirmed;

	/* ウィジェット */
	private JFrame owner;
	// 合計金額欄ラベル
	private JLabel totalPriceLabel;
	// 合計金額欄
	private JTextField totalPriceField;
	// お預かり欄ラベル
	private JLabel paidPriceLabel;
	// お預かり欄
	private JTextField paidPriceField;
	// 決済ボタン
	private JButton okButton;
	// 中止ボタン
	private JButton cancelButton;

	/*
	 * コンストラクタ
	 */
	public PaymentDialog(JFrame _owner, int _totalPrice) {
		super(_owner, true);
		owner = _owner;
		totalPrice = _totalPrice;
		confirmed = false;

		setLayout(null);
		setTitle("決済");
		setSize(248, 175);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();

		// 合計金額欄を生成する。
		totalPriceLabel = new JLabel("合計金額");
		totalPriceLabel.setBounds(16, 16, 100, 24);
		contentPane.add(totalPriceLabel);
		totalPriceField = new JTextField (8);
		totalPriceField.setBounds(116, 16, 100, 24);
		totalPriceField.setBackground(Color.YELLOW);
		totalPriceField.setHorizontalAlignment(JTextField.RIGHT);
		totalPriceField.setEditable(false);
		totalPriceField.setFocusable(false);
		totalPriceField.setText(Integer.toString(totalPrice));
		contentPane.add(totalPriceField);

		// お預かり欄を生成する。
		paidPriceLabel = new JLabel("お預かり");
		paidPriceLabel.setBounds(16, 48, 100, 24);
		contentPane.add(paidPriceLabel);
		paidPriceField = new JTextField (8);
		paidPriceField.setBounds(116, 48, 100, 24);
		paidPriceField.setBackground(Color.YELLOW);
		paidPriceField.setHorizontalAlignment(JTextField.RIGHT);
		paidPriceField.setEditable(true);
		contentPane.add(paidPriceField);

		// OKボタンを生成する。
		okButton = new JButton("決済");
		okButton.setBounds(26, 96, 80, 24);
		okButton.addActionListener(this);
		okButton.setActionCommand("ok");
		contentPane.add(okButton);

		// キャンセルボタンを生成する。
		cancelButton = new JButton("中止");
		cancelButton.setBounds(126, 96, 80, 24);;
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("cancel");
		contentPane.add(cancelButton);
	}

	/*
	 * 決済ダイアログを閉じるときにOKボタンが押されたかを返す。
	 */
	public Boolean isConfirmed() {
		return confirmed;
	}

	/*
	 * お預かり額を返す。
	 */
	public int getPaidPrice() {
		return paidPrice;
	}

	/*
	 * 決済の意思が確認されたときに呼び出される。
	 */
	private void paymentConfirmed() {
		try {
			paidPrice = Integer.parseInt(paidPriceField.getText());
		}
		catch (NumberFormatException ex) {
			paidPrice = 0;
			JOptionPane.showMessageDialog(owner, "お預かりの入力が不正です。", "エラー", JOptionPane.ERROR_MESSAGE);
			paidPriceField.requestFocusInWindow();
			return;
		}
		if (paidPrice < totalPrice) {
			JOptionPane.showMessageDialog(owner, "お預かりが不足しています。", "エラー", JOptionPane.ERROR_MESSAGE);
			paidPriceField.requestFocusInWindow();
			return;
		}
		confirmed = true;
		dispose();
	}

	/*
	 * 決済の意思が中止されたときに呼び出される。
	 */
	private void paymentCancelled() {
		paidPrice = 0;
		confirmed = false;
		dispose();
	}

	/*
	 * ボタンが押されたときに呼び出される。
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ok")) {
			paymentConfirmed();
		} else if (e.getActionCommand().equals("cancel")) {
			paymentCancelled();
		}
	}
}
