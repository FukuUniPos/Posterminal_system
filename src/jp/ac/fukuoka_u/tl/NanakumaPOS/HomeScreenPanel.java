//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  ホーム画面クラス。ユーザはホーム画面で POS 端末の機能選択を行う。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class HomeScreenPanel extends JPanel implements ActionListener {
	/* 関連先 */
	// POS端末全体制御
	private POSTerminalApp app;

	/* ウィジェット */
	// 商品チェックボタン
	private JButton checkArticlesButton;
	// 会員管理ボタン
	private JButton memberManagementButton;

	/*
	 * コンストラクタ。
	 */
	public HomeScreenPanel(POSTerminalApp _app) {
		// 関連先を初期化する。
		app = _app;

		// レイアウトマネージャを無効化する。
		setLayout(null);

		// 商品チェックボタンを生成する。
		checkArticlesButton = new JButton("商品チェック");
		checkArticlesButton.setBounds(240, 270, 144, 100);
		checkArticlesButton.addActionListener (this);
		checkArticlesButton.setActionCommand("checkArticles");
		add (checkArticlesButton);

		// 会員検索ボタンを生成する。
		memberManagementButton = new JButton("会員管理");
		memberManagementButton.setBounds(440, 270, 144,100);
		memberManagementButton.addActionListener (this);
		memberManagementButton.setActionCommand("memberManagement");
		add (memberManagementButton);
	}

	/*
	 * 商品チェックが選択されると呼び出される。
	 */
	private void checkArticlesRequested() {
		// POS端末全体制御に商品チェックの開始を要求する。
		app.checkArticlesRequested();
	}

	/*
	 * 会員管理が選択されると呼び出される。
	 */
	private void memberManagementRequested() {
		// POS端末全体制御に会員管理の開始を要求する。
		app.memberManagementRequested();
	}

	/*
	 * ホーム画面上のボタンが押されるとこのメソッドが呼び出される。
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals ("checkArticles")) {
			// 商品チェックボタンが押され，商品のチェックが指示された。
			checkArticlesRequested();
		} else if (cmd.equals ("memberManagement")) {
			// 会員検索ボタンが押され，会員検索が指示された。
			memberManagementRequested();
		}
	}
}
