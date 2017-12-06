//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  仮想キャッシュドロワインターフェースクラス。実際のキャッシュドロワと同一の
 *  インターフェースを持ち，実際のカスタマディスプレイの挙動を PC 上でシミュ
 *  レーションする。これにより開発時に POS 端末の実機がなくてもアプリケーション
 *  の振舞いを確認できる。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class VirtualCashDrawerIF extends AbstractedCashDrawerIF implements ActionListener {
	/*
	 * キャッシュドロワの状態を表す列挙型
	 */
	private enum DrawerState {
		Opening,	// 開いた状態
		Closing,	// 閉じた状態
	}

	/* 内部データ */
	// キャッシュドロワの状態
	private DrawerState drawerState;

	/* ウィジェット */
	// ウィンドウフレーム
	private JFrame cashDrawerFrame;
	// キャッシュドロワの画像を表示するラベル
	private JLabel drawerImage;
	// 開いているキャッシュドロワの画像
	private ImageIcon openingDrawerImage;
	// 閉じているキャッシュドロワの画像
	private ImageIcon closingDrawerImage;
	// 「閉じる」ボタン
	private JButton closeButton;

	/*
	 * コンストラクタ。
	 */
	public VirtualCashDrawerIF() {
		// 内部データを初期化する。
		drawerState = DrawerState.Closing;

		// フレームを生成する。
		cashDrawerFrame = new JFrame("キャッシュドロワ");
		cashDrawerFrame.setBounds(1380, 800, 326, 293);
		cashDrawerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		cashDrawerFrame.setResizable(false);
		cashDrawerFrame.getContentPane().setLayout(null);

		// 画像を準備する。
		openingDrawerImage = new ImageIcon("./img/opening_drawer.png");
		closingDrawerImage = new ImageIcon("./img/closing_drawer.png");

		// 画像を表示するラベルを生成する。
		drawerImage = new JLabel(closingDrawerImage);
		drawerImage.setBounds(0,  0,  320, 240);
		cashDrawerFrame.getContentPane().add(drawerImage);

		// 閉じるボタンを生成する。
		closeButton = new JButton ("閉じる");
		closeButton.setBounds(0, 240, 320, 24);
		closeButton.addActionListener(this);
		closeButton.setActionCommand("closeButton");
		cashDrawerFrame.getContentPane().add(closeButton);

		// フレームを可視にする。
		cashDrawerFrame.setVisible(true);
	}

	/*
	 * キャッシュドロワを開ける。
	 */
	@Override
	void openDrawer() {
		// キャッシュドロワを開いた状態に変更する。
		drawerState = DrawerState.Opening;
		// キャッシュドロワが開いている画像に変更する。
		drawerImage.setIcon(openingDrawerImage);
	}

	/*
	 * キャッシュドロワを閉める。
	 */
	@Override
	void closeDrawer() {
		// キャッシュドロワを閉めた状態に変更する。
		drawerState = DrawerState.Closing;
		// キャッシュドロワが閉じている画像に変更する。
		drawerImage.setIcon(closingDrawerImage);
	}

	/*
	 * ウィンドウ上のボタンを押すと呼び出される。
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("closeButton")) {
			// 「閉じる」ボタンが押されたときはキャッシュドロワを閉める。
			closeDrawer();
		}
	}
}
