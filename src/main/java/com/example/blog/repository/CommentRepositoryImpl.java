package com.example.blog.repository;

import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.util.DatabaseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    private DatabaseUtil databaseUtil;

    @Override
    public List<Comment> findByBlogId(Integer blogId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT c.*, u.id as user_id, u.username " +
                "FROM comments c " +
                "JOIN users u ON c.user_id = u.id " +
                "WHERE c.blog_id = ? AND c.deleted_at IS NULL " +
                "ORDER BY id DESC";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, blogId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setContent(rs.getString("content"));
                    comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    comment.setUpdatedAt(
                            rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime()
                                    : null);
                    comment.setDeletedAt(
                            rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime()
                                    : null);

                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    comment.setUser(user);

                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    @Override
    public Comment findById(Integer id) {
        Comment comment = null;
        String sql = "SELECT * FROM comments WHERE id = ?";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setContent(rs.getString("content"));
                    comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    comment.setUpdatedAt(
                            rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime()
                                    : null);
                    comment.setDeletedAt(
                            rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime()
                                    : null);

                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    comment.setUser(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comment;
    }

    @Override
    public void save(Comment comment) {
        String sql = "INSERT INTO comments (content, blog_id, user_id) VALUES (?, ?, ?)";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, comment.getContent());
            stmt.setInt(2, comment.getBlog().getId());
            stmt.setInt(3, comment.getUser().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Comment comment) {
        String sql = "UPDATE comments SET content = ?, updated_at = ? WHERE id = ?";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, comment.getContent());
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, comment.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "UPDATE comments SET deleted_at = NOW() WHERE id = ?";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByBlogId(Integer blogId) {
        String sql = "UPDATE comments SET deleted_at = NOW() WHERE blog_id = ?";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, blogId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
