package com.bankapp.dao;

import com.bankapp.model.Account;
import com.bankapp.util.DBConnection;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MySQLAccountDAO implements AccountDAO {

    private Connection conn;

    public MySQLAccountDAO() throws Exception {
        this.conn = DBConnection.getConnection();
    }

    @Override
    public Account create(Account account) throws Exception {
        String sql = "INSERT INTO account (customer_id, account_number, balance) VALUES (?, ?, ?);";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, account.getCustomerId());
            st.setString(2, account.getAccountNumber());
            st.setBigDecimal(3, account.getBalance());
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    account.setId(rs.getInt(1));
                }
            }
            return account;
        }
    }

    @Override
    public Account findById(int id) throws Exception {
        String sql = "SELECT * FROM account WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public Account findByAccountNumber(String accountNumber) throws Exception {
        String sql = "SELECT * FROM account WHERE account_number = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, accountNumber);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public List<Account> findAll() throws Exception {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public boolean update(Account account) throws Exception {
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setBigDecimal(1, account.getBalance());
            st.setInt(2, account.getId());
            return st.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM account WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        }
    }

    @Override
    public boolean transfer(String fromAccount, String toAccount, double amount) throws Exception {
        conn.setAutoCommit(false);
        try {
            Account from = findByAccountNumber(fromAccount);
            Account to = findByAccountNumber(toAccount);
            if (from == null || to == null) throw new Exception("Cuenta no encontrada");
            if (from.getBalance().doubleValue() < amount) throw new Exception("Fondos insuficientes");

            BigDecimal newFrom = from.getBalance().subtract(BigDecimal.valueOf(amount));
            BigDecimal newTo = to.getBalance().add(BigDecimal.valueOf(amount));

            String uSql = "UPDATE account SET balance = ? WHERE id = ?";
            try (PreparedStatement ust = conn.prepareStatement(uSql)) {
                ust.setBigDecimal(1, newFrom);
                ust.setInt(2, from.getId());
                ust.executeUpdate();

                ust.setBigDecimal(1, newTo);
                ust.setInt(2, to.getId());
                ust.executeUpdate();
            }

            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (Exception e) {
            conn.rollback();
            conn.setAutoCommit(true);
            throw e;
        }
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        Account a = new Account();
        a.setId(rs.getInt("id"));
        a.setCustomerId(rs.getInt("customer_id"));
        a.setAccountNumber(rs.getString("account_number"));
        a.setBalance(rs.getBigDecimal("balance"));
        return a;
    }
}