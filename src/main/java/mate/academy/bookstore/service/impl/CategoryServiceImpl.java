package mate.academy.bookstore.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.BookDtoWithoutCategoryIds;
import mate.academy.bookstore.dto.CategoryDto;
import mate.academy.bookstore.dto.CategoryRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.BookMapper;
import mate.academy.bookstore.mapper.CategoryMapper;
import mate.academy.bookstore.model.Category;
import mate.academy.bookstore.repository.BookRepository;
import mate.academy.bookstore.repository.CategoryRepository;
import mate.academy.bookstore.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Can not find Category with id: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto save(CategoryRequestDto categoryDto) {
        Category category = categoryRepository.save(categoryMapper.toEntity(categoryDto));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long id, CategoryRequestDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can not find Category with id: " + id));
        categoryMapper.updateCategoryFromDto(categoryDto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Can not find Category with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            Long id, Pageable pageable) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find Category with id: " + id));
        return bookRepository.findAllByCategories_Id(id, pageable)
                .map(bookMapper::toDtoWithoutCategories);
    }
}
