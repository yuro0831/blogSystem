package com.example.blog.repository;

<<<<<<< Updated upstream
import com.example.blog.entity.Blog;
import com.example.blog.entity.User;
import com.example.blog.entity.Category;
import com.example.blog.util.DatabaseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

=======
>>>>>>> Stashed changes
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< Updated upstream
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlogRepositoryImpl implements BlogRepository {

    @Autowired
    private DatabaseUtil databaseUtil;

    @Override
    public List<Blog> findAll() {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT b.*, u.id as user_id, u.username, u.email, c.id as category_id, c.name_ja " +
                "FROM blogs b " +
                "JOIN users u ON b.user_id = u.id " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "WHERE b.deleted_at IS NULL " +
                "ORDER BY id DESC";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Blog blog = new Blog();
                blog.setId(rs.getInt("id"));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                blog.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                blog.setDeletedAt(
                        rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null);

                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                blog.setUser(user);

                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setNameJa(rs.getString("name_ja"));
                blog.setCategory(category);

                blogs.add(blog);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blogs;
    }

    @Override
    public Blog findById(Integer id) {
        Blog blog = null;
        String sql = "SELECT b.*, u.id as user_id, u.username, u.email, c.id as category_id, c.name_ja " +
                "FROM blogs b " +
                "JOIN users u ON b.user_id = u.id " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "WHERE b.id = ? AND b.deleted_at IS NULL";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    blog = new Blog();
                    blog.setId(rs.getInt("id"));
                    blog.setTitle(rs.getString("title"));
                    blog.setContent(rs.getString("content"));
                    blog.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    blog.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    blog.setDeletedAt(
                            rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime()
                                    : null);

                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    blog.setUser(user);

                    Category category = new Category();
                    category.setId(rs.getInt("category_id"));
                    category.setNameJa(rs.getString("name_ja"));
                    blog.setCategory(category);
                }
            }

=======
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.blog.entity.Blog;
import com.example.blog.util.DatabaseUtil;

@Repository
public class BlogRepositoryImpl implements BlogRepository {

    private final DatabaseUtil databaseUtil;

    public BlogRepositoryImpl(DatabaseUtil databaseUtil) {
        this.databaseUtil = databaseUtil;
    }

    @Override
    public List<Blog> findAll() {
        List<Blog> blogList = new ArrayList<>();
        String sql = "SELECT * FROM blogs WHERE deleted_at IS NULL ORDER BY id DESC";

        try (Connection connection = this.databaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                Blog blog = mapResultSetToBlog(result);
                blogList.add(blog);
            }
>>>>>>> Stashed changes
        } catch (SQLException e) {
            e.printStackTrace();
        }

<<<<<<< Updated upstream
        return blog;
=======
        return blogList;
    }

    @Override
    public Blog findById(int id) {
        Blog blog = null;
        String sql = "SELECT * FROM blogs WHERE id = ? AND deleted_at IS NULL";

        try (Connection connection = this.databaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    blog = mapResultSetToBlog(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blog; // 該当のブログが見つからなければ null を返す
>>>>>>> Stashed changes
    }

    @Override
    public void save(Blog blog) {
<<<<<<< Updated upstream
        String sql = "INSERT INTO blogs (title, content, user_id, category_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setInt(3, blog.getUser().getId());
            stmt.setInt(4, blog.getCategory().getId());
            stmt.executeUpdate();

=======
        // バリデーション - タイトルとコンテンツは必須
        if (blog.getTitle() == null || blog.getTitle().isEmpty()) {
            throw new IllegalArgumentException("タイトルは必須です。");
        }
        if (blog.getContent() == null || blog.getContent().isEmpty()) {
            throw new IllegalArgumentException("内容は必須です。");
        }

        String insertSql = "INSERT INTO blogs (title, content, created_at, updated_at, deleted_at) VALUES (?, ?, NOW(), NOW(), NULL)";
        String updateSql = "UPDATE blogs SET title = ?, content = ?, updated_at = NOW() WHERE id = ?";
        boolean isNew = (blog.getId() == null);

        try (Connection connection = this.databaseUtil.getConnection()) {
            connection.setAutoCommit(false); // 自動コミットを無効にする

            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    isNew ? insertSql : updateSql,
                    Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, blog.getTitle());
                preparedStatement.setString(2, blog.getContent());

                if (!isNew) {
                    preparedStatement.setInt(3, blog.getId());
                }

                preparedStatement.executeUpdate();

                if (isNew) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            blog.setId(generatedKeys.getInt(1));
                        }
                    }
                }

                connection.commit(); // 変更をコミットする
            } catch (SQLException e) {
                connection.rollback(); // エラーが発生したらロールバックする
                throw e;
            }
>>>>>>> Stashed changes
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Blog blog) {
<<<<<<< Updated upstream
        String sql = "UPDATE blogs SET title = ?, content = ?, category_id = ?, updated_at = ? WHERE id = ?";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setInt(3, blog.getCategory().getId());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(5, blog.getId());
            stmt.executeUpdate();

=======
        // バリデーション - タイトルとコンテンツは必須
        if (blog.getTitle() == null || blog.getTitle().isEmpty()) {
            throw new IllegalArgumentException("タイトルは必須です。");
        }
        if (blog.getContent() == null || blog.getContent().isEmpty()) {
            throw new IllegalArgumentException("内容は必須です。");
        }

        String updateSql = "UPDATE blogs SET title = ?, content = ?, updated_at = NOW() WHERE id = ?";
        
        try (Connection connection = this.databaseUtil.getConnection()) {
            connection.setAutoCommit(false); // 自動コミットを無効にする

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                preparedStatement.setString(1, blog.getTitle());
                preparedStatement.setString(2, blog.getContent());
                preparedStatement.setInt(3, blog.getId());

                preparedStatement.executeUpdate();

                connection.commit(); // 変更をコミットする
            } catch (SQLException e) {
                connection.rollback(); // エラーが発生したらロールバックする
                throw e;
            }
>>>>>>> Stashed changes
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
<<<<<<< Updated upstream
    public void delete(Integer id) {
        String sql = "UPDATE blogs SET deleted_at = NOW() WHERE id = ?";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

=======
    public void deleteById(int id) {
        String sql = "UPDATE blogs SET deleted_at = NOW() WHERE id = ?";

        try (Connection connection = this.databaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false); // 自動コミットを無効にする
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("ブログが見つかりません: ID = " + id);
            }

            connection.commit(); // 変更をコミットする
>>>>>>> Stashed changes
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

<<<<<<< Updated upstream
    @Override
    public Integer countByTitle(String title) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM blogs WHERE title = ? AND deleted_at IS NULL";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public Integer countByTitleAndNotId(String title, Integer id) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM blogs WHERE title = ? AND id != ? AND deleted_at IS NULL";

        try (Connection conn = databaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setInt(2, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
=======
    // ヘルパーメソッド: ResultSet から Blog オブジェクトを作成
    private Blog mapResultSetToBlog(ResultSet result) throws SQLException {
        Blog blog = new Blog();
        blog.setId(result.getInt("id"));
        blog.setTitle(result.getString("title"));
        blog.setContent(result.getString("content"));
        blog.setCreatedAt(result.getTimestamp("created_at").toLocalDateTime());
        blog.setUpdatedAt(result.getTimestamp("updated_at").toLocalDateTime());
        return blog;
>>>>>>> Stashed changes
    }
}
