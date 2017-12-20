//-*- java -*-
/******************************************************************************
 *
 *  福岡大学工学部電子情報工学科プロジェクト型ソフトウェア開発演習教材
 *
 *  Copyright (C) 2015 プロジェクト型ソフトウェア開発演習実施チーム
 *
 *  外部データベースとインターフェースをとるクラス。
 *
 *****************************************************************************/

package jp.ac.fukuoka_u.tl.NanakumaPOS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.ac.fukuoka_u.tl.NanakumaPOS.Member.Gender;

//import com.mysql.jdbc.Statement;

public class DBServerIF {
	private Connection conn;
	private String url = "jdbc:mysql://hamburg.tl.fukuoka-u.ac.jp/nanakumapos5";
	private String user = "b3pbl";
	private String password = "nanakumapbl";

	public class DBServerIFException extends Exception {
		private String message;
		public DBServerIFException(String _message) {
			message = _message;
		}
		public String getMessage() {
			return message;
		}
	}

	/*
	 *  コンストラクタ。
	 */
	public DBServerIF () {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, user, password);
			//conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 *  商品コード articleCode の商品を検索する。
	 */
	public Article findArticle(String articleCode) throws DBServerIFException {
		Article article = null;

		try {
			int count;
			Statement stmt = conn.createStatement();
			String sql = "select * from articletbl where code = '" + articleCode + "';";
			ResultSet rs = stmt.executeQuery(sql);
			count = 0;
			while (rs.next()) {
				article = new Article(rs.getString("code"), rs.getString("name"), rs.getInt("price"));
				count++;
			}
			if (count < 1) {
				throw new DBServerIFException("この商品はデータベースに登録されていません。");
			}
			if (count > 1) {
				throw new DBServerIFException("この商品はデータベースに重複登録されています。");
			}
			rs.close();
		}
		catch (SQLException ex) {
			throw new DBServerIFException("SQLException: " + ex.getMessage());
		}
		return article;
	}

	/*
	 * 会員番号 membershipID の会員を検索する。
	 */
	public Member findMember(String membershipID) throws DBServerIFException {
		Member member = null;
		Gender gender = Gender.Male;

		try {
			int count;
			Statement stmt = conn.createStatement();
			String sql = "select * from membertbl where id='" + membershipID + "';";
			ResultSet rs = stmt.executeQuery(sql);
			count = 0;
			while (rs.next()) {
				if (rs.getString("gender").equals("m")) {
					gender = Gender.Male;
				} else if (rs.getString("gender").equals("f")) {
					gender = Gender.Female;
				}
				member = new Member(rs.getString("id"), rs.getString("name"), rs.getString("furigana"), gender);
				count++;
			}
			if (count < 1) {
				throw new DBServerIFException("この会員はデータベースに登録されていません。");
			}
			if (count > 1) {
				throw new DBServerIFException("この会員はデータベースに重複登録されています。");
			}
			rs.close();
		}
		catch (SQLException ex) {
			throw new DBServerIFException("SQLException: " + ex.getMessage());
		}
		return member;
	}

	/*
	 * 会員情報の登録を受け付ける。
	 * 会員情報 member のとおりにデータベースに登録する。
	 */
	public void registerMember(Member member) throws DBServerIFException {
		String gender = "m";
		if(member.getGender() == Gender.Male) {
			gender = "m";
		} else if(member.getGender() == Gender.Female) {
			gender = "f";
		}
		try {
			Statement stmt = conn.createStatement();
			String sql = "insert into membertbl values (" + member.getID() + "," + member.getName() + ",'" + gender + "'," + member.getGender() + ");";
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException ex) {
			// エラー文未入力
			if(ex.getErrorCode() == 1062) {
				throw new DBServerIFException("この会員情報はすでに登録済みです。");
			}
			else throw new DBServerIFException("SQLException: " + ex.getMessage());
		}
	}

	/*
	 * 会員情報の変更要求を受け付ける。
	 * 会員情報 member のとおりにデータベースを更新する。
	 */
	public void updateMember(Member member) throws DBServerIFException {
		String gender = "m";
		if(member.getGender() == Gender.Male) {
			gender = "m";
		} else if(member.getGender() == Gender.Female) {
			gender = "f";
		}
		try {
			Statement stmt = conn.createStatement();
			String sql = "update membertbl set name='" + member.getName() + "', furigana = '" + member.getFurigana() + "', gender = '" + gender + "' where id = '" + member.getID() + "';";
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException ex) {
			throw new DBServerIFException("SQLException: " + ex.getMessage());
		}
	}

	/*
	 * 会員番号 memberID の会員を削除する。
	 */
	public void deleteMember(String memberID) throws DBServerIFException {
		//@@@ 未実装
		//@@@ 削除する会員の購入履歴も削除しなければならないことに注意。
		try {
			Statement stmt = conn.createStatement();
			String sql = "delete from membertbl where id='" + memberID + "';";
			stmt.executeUpdate(sql);
			//購入履歴のテーブル名未記入
			sql = "delete from ______ where id='" + memberID + "';";
			stmt.executeUpdate(sql);

			stmt.close();
		}
		catch (SQLException ex) {
			throw new DBServerIFException("SQLException: " + ex.getMessage());
		}
	}

}
