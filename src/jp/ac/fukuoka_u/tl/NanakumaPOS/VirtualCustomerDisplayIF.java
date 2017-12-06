//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  仮想カスタマディスプレイインターフェースクラス。実際のカスタマディスプレイ
 *  と同一のインターフェースを持ち，実際のカスタマディスプレイの挙動を PC 上で
 *  シミュレーションする。これにより開発時に POS 端末の実機がなくてもアプリケー
 *  ションの振舞いを確認できる。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class VirtualCustomerDisplayIF extends AbstractedCustomerDisplayIF {
	/* ウィジェット */
	// ウィンドウフレーム
	private JFrame customerDisplayFrame;
	// 上ラインを表示するラベル
	private JLabel upperLine;
	// 下ラインを表示するラベル
	private JLabel lowerLine;
	// フォント
	private Font font;

	/*
	 * コンストラクタ。
	 */
	public VirtualCustomerDisplayIF () {
		// フレームを初期化する。
		customerDisplayFrame = new JFrame ("カスタマディスプレイ");
		customerDisplayFrame.setBounds(0, 0, 174, 85);
		customerDisplayFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		customerDisplayFrame.setResizable(false);
		customerDisplayFrame.getContentPane().setLayout(null);

		// 固定幅フォントを準備する。
		font = new Font ("ＭＳ ゴシック", Font.PLAIN, 16);

		// 上下のラインを初期化する。
		upperLine = new JLabel ();
		upperLine.setBounds(4, 4, 160, 24);
		upperLine.setFont(font);
		upperLine.setOpaque(true);
		upperLine.setBackground(Color.BLACK);
		upperLine.setForeground(Color.CYAN);
		customerDisplayFrame.getContentPane().add(upperLine);
		lowerLine = new JLabel ();
		lowerLine.setBounds(4, 28, 160, 24);
		lowerLine.setFont(font);
		lowerLine.setOpaque(true);
		lowerLine.setBackground(Color.BLACK);
		lowerLine.setForeground(Color.CYAN);
		customerDisplayFrame.getContentPane().add(lowerLine);

		// フレームを可視にする。
		customerDisplayFrame.setVisible(true);
	}

	/*
	 * カスタマディスプレイの表示をクリアする。
	 */
	@Override
	public void clear() {
		upperLine.setText("");
		lowerLine.setText("");
	}

	/*
	 * カスタマディスプレイのライン line にメッセージ message を表示する。
	 * 左寄せ，センタリング，右寄せは alignment で指定する。
	 */
	protected void displayMessage (JLabel label, String message, Alignment alignment) {
		switch (alignment) {
		case LEFT:
			label.setText (message);
			label.setHorizontalAlignment (SwingConstants.LEFT);
			break;
		case CENTERING:
			label.setText (message);
			label.setHorizontalAlignment (SwingConstants.CENTER);
			break;
		case RIGHT:
			label.setText (message);
			label.setHorizontalAlignment (SwingConstants.RIGHT);
			break;
		}
	}

	/*
	 * カスタマディスプレイの上ラインにメッセージ message を表示する。
	 * 左寄せ，センタリング，右寄せは alignment で指定する。
	 */
	@Override
	public void displayUpperMessage (String message, Alignment alignment) {
		displayMessage (upperLine, message, alignment);
	}

	/*
	 * カスタマディスプレイの下ラインにメッセージ message を表示する。
	 * 左寄せ，センタリング，右寄せは alignment で指定する。
	 */
	@Override
	public void displayLowerMessage (String message, Alignment alignment) {
		displayMessage (lowerLine, message, alignment);
	}
}
