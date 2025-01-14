package com.treasurehunt.treasurehunt.db.mysql;

import com.treasurehunt.treasurehunt.entity.Listing;
import com.treasurehunt.treasurehunt.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQL {

    private static final String USERS_DB = "users";
    private static final String LISTINGS_DB = "listings";
    private static final String SAVED_RECORDS_DB = "saved_records";

    // Create user in users db
    public static String createUser(DataSource pool, User user) throws MySQLException {
        try (Connection conn = pool.getConnection()) {
            // TODO
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            throw new MySQLException("Failed to create User");
        }
    }

    // Create new listing in listings db
    public static void createListing(DataSource pool, Listing listing) throws MySQLException {

        // Create a connection from the given pool
        try (Connection conn = pool.getConnection()) {

            // Insert the new data to listingsDB
            String sql = "INSERT INTO listings VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement postListing = conn.prepareStatement(sql)) {

                postListing.setString(1, listing.getListingId());
                postListing.setString(2, listing.getTitle());
                postListing.setDouble(3, listing.getPrice());
                postListing.setString(4, listing.getCategory());
                postListing.setString(5, listing.getDescription());
                postListing.setString(6, listing.getItemCondition());
                postListing.setString(7, listing.getBrand());
                postListing.setString(8, listing.getPictureUrls());
                postListing.setString(9, listing.getSellerId());
                postListing.setString(10, listing.getSellerName());
                postListing.setString(11, listing.getAddress());
                postListing.setTimestamp(12, new java.sql.Timestamp(System.currentTimeMillis()));

                postListing.executeUpdate();

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MySQLException("Failed to create listing");
        }
    }

    // Update an existing listing in listings db
    public static String updateListing(DataSource pool, Listing listing) throws MySQLException {
        // Create a connection from the given pool
        try (Connection conn = pool.getConnection()) {
            // TODO
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            throw new MySQLException("Failed to update Listing");
        }
    }

    // Delete an existing listing in listings db
    public static String deleteListing(DataSource pool, Listing listing) throws MySQLException {
        // Create a connection from the given pool
        try (Connection conn = pool.getConnection()) {
            // TODO
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            throw new MySQLException("Failed to delete Listing");
        }
    }

    // Get listings created by user
    public static List<Listing> getMyListings(DataSource pool, String userId) throws MySQLException {
        // Build a return object
        List<Listing> myListings = new ArrayList<>();

        // Create a connection from the given pool
        try (Connection conn = pool.getConnection()) {

            // Query users DB for userId == seller_id
            // TODO

            // Add listings in ResultSet to myListings
            // TODO

            // Return myListings
            return myListings;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MySQLException("Failed to get my listings");
        }
    }

    // Get listings saved by user
    public static List<Listing> getSavedListings(DataSource pool, String userId) throws MySQLException {
        // Build a return object
        List<Listing> savedListings = new ArrayList<>();

        // Create a connection from the given pool
        try (Connection conn = pool.getConnection()) {

            // Query saved_records DB for userId == user_id and query listings DB with listing_id's
            // Use JOIN to optimize
            // TODO

            // Add listings in ResultSet to savedListings
            // TODO

            // Return savedListings
            return savedListings;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MySQLException("Failed to get saved listings");
        }
    }

    // Create a saved record in saved_records
    public static void saveListing(DataSource pool, String userId, String listingId) throws MySQLException {
        // Create a connection from the given pool
        try (Connection conn = pool.getConnection()) {
            // Build and execute SQL statement
            // TODO
            String sql = "";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MySQLException("Failed to save listing");
        }
    }

    // Delete a saved record in saved_records
    public static void unsaveListing(DataSource pool, String userId, String listingId) throws MySQLException {
        // Create a connection from the given pool
        try (Connection conn = pool.getConnection()) {
            // Build and execute SQL statement
            // TODO
            String sql = "";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MySQLException("Failed to unsave listing");
        }
    }

    // Need to change this to getUser
    // TODO
    public static String[] getSellerNameAddress(DataSource pool, String SellerID) throws MySQLException {

        // Hardcoded, Change later
        // TODO
        String[] result = new String[3];

        try (Connection conn = pool.getConnection()) {
            String sql = "SELECT first_name, last_name, address FROM users WHERE user_id = ?";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, SellerID);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    result[0] = rs.getString("first_name");
                    result[1] = rs.getString("last_name");
                    result[2] = rs.getString("address");
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new MySQLException("Failed to get user from DB");
        }
        return result;
    }

    // Get Listing from listings db
    public static Listing getListing(DataSource pool, String listingId) throws MySQLException {
        Listing listing = new Listing();

        try (Connection conn = pool.getConnection()) {
            String sql = "SELECT * FROM listings WHERE listing_id = ?";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, listingId);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Listing.Builder builder = new Listing.Builder();
                    builder.setListingId(listingId)
                            .setTitle(rs.getString("title"))
                            .setPrice(rs.getDouble("price"))
                            .setCategory(rs.getString("category"))
                            .setDescription(rs.getString("description"))
                            .setItemCondition(rs.getString("item_condition"))
                            .setBrand(rs.getString("brand"))
                            .setPictureUrls(rs.getString("picture_urls"))
                            .setSellerId(rs.getString("seller_id"))
                            .setSellerName(rs.getString("seller_name"))
                            .setAddress(rs.getString("address"))
                            .setDate(rs.getString("date"));
                    listing = builder.build();
                }
            }
            return listing;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new MySQLException("Failed to get listing from DB");
        }
    }
}
