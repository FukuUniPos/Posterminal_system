//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  ある一種類の「商品」を表すクラス。その「商品」の商品コード，名称，単価を
 *  記録する。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

public class Article {
	/* データメンバ */
	//  商品コード
	private String articleCode;
	//  商品名
	private String articleName;
	//  定価
	private int cataloguePrice;

	/*
	 * コンストラクタ。
	 */
	public Article (String _articleCode, String _articleName, int _cataloguePrice) {
		articleCode = _articleCode;
		articleName = _articleName;
		cataloguePrice = _cataloguePrice;
	}

	/*
	 * 商品コードを取得する。
	 */
	public String getArticleCode () {
		return articleCode;
	}

	/*
	 * 商品名を取得する。
	 */
	public String getArticleName () {
		return articleName;
	}

	/*
	 * 定価を取得する。
	 */
	public int getCataloguePrice () {
		return cataloguePrice;
	}
}
