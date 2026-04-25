package lk.ruhuna.ems.controller;

import lk.ruhuna.ems.dto.response.*;
import lk.ruhuna.ems.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/events/{eventId}")
    public ResponseEntity<ApiResponse<BookingResponse>> book(
            @PathVariable Long eventId,
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registered successfully", bookingService.bookEvent(eventId, user.getUsername())));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponse>> cancel(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled", bookingService.cancelBooking(bookingId, user.getUsername())));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> myBookings(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(ApiResponse.success("Your bookings", bookingService.getMyBookings(user.getUsername())));
    }

    @GetMapping("/reference/{ref}")
    public ResponseEntity<ApiResponse<BookingResponse>> byReference(@PathVariable String ref) {
        return ResponseEntity.ok(ApiResponse.success("Booking found", bookingService.getByReference(ref)));
    }

    @PostMapping("/{bookingId}/export")
    public ResponseEntity<ApiResponse<String>> export(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal UserDetails user) {
        String path = bookingService.exportToFile(bookingId, user.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Exported to: " + path, path));
    }

    @GetMapping("/event/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> forEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(ApiResponse.success("Event bookings", bookingService.getBookingsForEvent(eventId)));
    }
}