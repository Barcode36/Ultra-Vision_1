package com.raul.rental_shop.Ultra_Vision.model.rental;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.raul.rental_shop.Ultra_Vision.model.DAO;
import com.raul.rental_shop.Ultra_Vision.model.Database;

public class RentalDAO implements DAO<RentalEntity> {

	List<RentalEntity> rts = new ArrayList<>();
	
	@SuppressWarnings("static-access")
	@Override
	public RentalEntity get(int index) throws SQLException {
		
		String sql = "SELECT r.*, CONCAT(c.firstname, ' ', c.lastname) AS fullname,"
				+ " t.name, t.media_format FROM rental r"
				+ " INNER JOIN customer c ON c.membership_card = r.customer_membership_card"
				+ " INNER JOIN title t ON t.code = r.title_code"
				+ " WHERE r.customer_membership_card = ?";
		
		Database.setPreparedStmt(sql)
			.getPreparedStmt()
			.setInt(1, index);
		
		ResultSet rs = Database.getPreparedStmt()
				.executeQuery();
		
		RentalEntity rt = new RentalEntity();
		
		while(rs.next()) {
			
			rt.setCustomerMembershipNumber(rs.getInt("customer_membership_card"));
			rt.setTitleCode(rs.getInt("title_code"));
			rt.setRentAt(Timestamp.valueOf(rs.getString("rent_at")));
			rt.setReturnAt(Timestamp.valueOf(rs.getString("return_at")));
			boolean returned = (rs.getByte("is_returned") == 1) ? true : false;
			rt.setReturned(returned);
		}
		
		return rt;
	}

	@Override
	public List<RentalEntity> getAll() throws SQLException {
		
		String sql = "SELECT r.*, CONCAT(c.firstname, ' ', c.lastname) AS fullname,"
				+ " t.name, t.media_format FROM rental r"
				+ " INNER JOIN customer c ON c.membership_card = r.customer_membership_card"
				+ " INNER JOIN title t ON t.code = r.title_code";
		
		ResultSet rs = Database.getStatment().executeQuery(sql);
		
		while(rs.next()) {
			
			RentalEntity rt = new RentalEntity();
			
			rt.setCustomerMembershipNumber(rs.getInt("customer_membership_card"));
			rt.setTitleCode(rs.getInt("title_code"));
			rt.setRentAt(Timestamp.valueOf(rs.getString("rent_at")));
			rt.setReturnAt(Timestamp.valueOf(rs.getString("return_at")));
			boolean returned = (rs.getByte("is_returned") == 1) ? true : false;
			rt.setReturned(returned);
			rt.setFullname(rs.getString("fullname"));
			rt.setTitleName(rs.getString("name"));
			rt.setMediaFormat(rs.getString("media_format"));
			
			this.rts.add(rt);
		}
		
		return rts;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean add(RentalEntity t) throws SQLException {
		
		String sql = "INSERT INTO rental (customer_membership_card,"
				+ " title_code, rent_at, return_at, is_returned)"
				+ "VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement stmt = Database.setPreparedStmt(sql).getPreparedStmt();
		
		stmt.setInt(1, t.getCustomerMembershipNumber());
		stmt.setInt(2, t.getTitleCode());
		stmt.setString(3, t.getRentAt().toString());
		stmt.setString(4, t.getReturnAt().toString());
		byte returned = (byte) ((t.isReturned()) ? 1 : 0);
		stmt.setByte(5, returned);
		
		int result = Database.getPreparedStmt().executeUpdate();
		
		return (result > 0) ? true : false;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean remove(RentalEntity t) throws SQLException {
		
		String sql = "DELETE FROM rental WHERE customer_membership_card = ?";
		
		Database.setPreparedStmt(sql)
			.getPreparedStmt()
			.setInt(1, t.getCustomerMembershipNumber());
		
		int result = Database.getPreparedStmt().executeUpdate();
		
		return (result > 0) ? true : false;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean update(RentalEntity t) throws SQLException {
		
		String sql = "UPDATE rental SET title_code = ?, rent_at = ?,"
				+ " return_at = ?, is_returned = ?"
				+ " WHERE customer_membership_card = ?";
		
		Database.getInstance().connect().setPreparedStmt(sql);
		PreparedStatement stmt = Database.getPreparedStmt();
		
		stmt.setInt(1, t.getTitleCode());
		stmt.setString(2, t.getRentAt().toString());
		stmt.setString(3, t.getReturnAt().toString());
		byte returned = (byte) ((t.isReturned()) ? 1 : 0);
		stmt.setByte(4, returned);
		stmt.setInt(5, t.getCustomerMembershipNumber());
		
		int result = stmt.executeUpdate();
		
		return (result > 0) ? true : false;
	}

	@SuppressWarnings("static-access")
	@Override
	public List<RentalEntity> search(String text) throws SQLException {
		
		String sql = "SELECT * FROM rental WHERE customer_membership_card LIKE ? "
				+ "OR title_code LIKE ? OR rent_at LIKE ?";
		
		Database.getInstance().connect().setPreparedStmt(sql);
		PreparedStatement stmt = Database.getPreparedStmt();
		
		stmt.setString(1, text);
		stmt.setString(2, text);
		stmt.setString(3, text);
		
		ResultSet rs = Database.getPreparedStmt()
				.executeQuery();
		
		while(rs.next()) {
			
			RentalEntity rt = new RentalEntity();
			
			rt.setCustomerMembershipNumber(rs.getInt("customer_membership_card"));
			rt.setTitleCode(rs.getInt("title_code"));
			rt.setRentAt(Timestamp.valueOf(rs.getString("rent_at")));
			rt.setReturnAt(Timestamp.valueOf(rs.getString("return_at")));
			boolean returned = (rs.getByte("is_returned") == 1) ? true : false;
			rt.setReturned(returned);
			
			this.rts.add(rt);
		}
		
		return rts;
	}

	
}
