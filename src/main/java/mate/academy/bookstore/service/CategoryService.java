package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.BookDtoWithoutCategoryIds;
import mate.academy.bookstore.dto.CategoryDto;
import mate.academy.bookstore.dto.CategoryRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CategoryRequestDto categoryDto);

    CategoryDto update(Long id, CategoryRequestDto categoryDto);

    void deleteById(Long id);

    Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable);
}
