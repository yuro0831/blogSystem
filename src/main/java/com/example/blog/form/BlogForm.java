package com.example.blog.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class BlogForm {

    private Integer id; // ブログの ID

    @NotBlank(message = "タイトルは必須項目です。")
    @Size(max = 255, message = "タイトルは255文字以内で入力してください。")
    private String title;

    @NotBlank(message = "内容は必須項目です。")
    private String content;

}
