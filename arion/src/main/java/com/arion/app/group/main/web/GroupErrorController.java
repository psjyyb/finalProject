/*
 * package com.arion.app.group.main.web;
 * 
 * import org.springframework.web.bind.annotation.ControllerAdvice; import
 * org.springframework.web.bind.annotation.ExceptionHandler; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.servlet.mvc.support.RedirectAttributes;
 * 
 * @ControllerAdvice public class GroupErrorController {
 * 
 * @GetMapping("/arion/error") public String arionError() { return
 * "/common/error/404"; }
 * 
 * 
 * @ExceptionHandler(Exception.class) public String handleException(Exception
 * ex, RedirectAttributes redirectAttributes) { // 에러 메세지를 전달
 * redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
 * 
 * // 에러 발생 시 이동할 페이지를 리턴 return "redirect:/arion/error"; } }
 */