//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  抽象カスタマディスプレイインターフェースクラス。実際のカスタマディスプレイ
 *  と仮想カスタマディスプレイの共通のインターフェース，データ構造，振舞いを定
 *  義する。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

abstract class AbstractedCustomerDisplayIF {
	/*
	 * カスタマディスプレイのライン表示を左寄せ，センタリング，右寄せのいずれに
	 * するかを指定する列挙型
	 */
	public enum Alignment {
		LEFT,		// 左寄せ
		CENTERING,	// センタリング
		RIGHT,		// 右寄せ
	}

	/*
	 * コンストラクタ。抽象クラスのインスタンスが作られることはないので
	 * アクセス可能性を protected としている。
	 */
	protected AbstractedCustomerDisplayIF() {
	}

	/*
	 * カスタマディスプレイの表示をクリアする。
	 */
	abstract public void clear();

	/*
	 * カスタマディスプレイの上ラインにメッセージ message を表示する。
	 * 左寄せ，センタリング，右寄せは alignment で指定する。
	 */
	abstract public void displayUpperMessage(String message, Alignment alignment);

	/*
	 * カスタマディスプレイの下ラインにメッセージ message を表示する。
	 * 左寄せ，センタリング，右寄せは alignment で指定する。
	 */
	abstract public void displayLowerMessage(String message, Alignment alignment);
}
