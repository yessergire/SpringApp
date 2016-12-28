package app.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class PaginationService {

    public void addPagination(Model model, Page page) {
	model.addAttribute("current", page.getNumber());
	model.addAttribute("total", page.getTotalPages());
	int begin = Math.max(0, page.getNumber() - 5);
	int end = Math.min(page.getNumber() + 5, page.getTotalPages()) - 1;
	List<Integer> list = IntStream.rangeClosed(begin, end).boxed().collect(Collectors.toList());
	model.addAttribute("integer_list", list);
	model.addAttribute("begin", begin);
	model.addAttribute("end", end);
	model.addAttribute("products", page.getContent());
    }
}
