// ============================================================
//  api.js — Shared JS for Event Management System
//  University of Ruhuna, Faculty of Technology
// ============================================================

const API_BASE = 'http://localhost:8080/api';

// ---- Toast Notification ----
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    if (!toast) return;
    toast.textContent = message;
    toast.className = `show ${type}`;
    setTimeout(() => { toast.className = ''; }, 3500);
}

// ---- Generic API call helper ----
async function apiFetch(url, options = {}) {
    try {
        const res = await fetch(API_BASE + url, {
            headers: { 'Content-Type': 'application/json', ...options.headers },
            ...options
        });
        const data = await res.json();
        if (!res.ok) {
            const msg = data.message || data.error || 'An error occurred';
            throw new Error(msg);
        }
        return data;
    } catch (err) {
        throw err;
    }
}

// ---- Format date for display ----
function formatDate(dateStr) {
    if (!dateStr) return '—';
    const d = new Date(dateStr + 'T00:00:00');
    return d.toLocaleDateString('en-GB', { day: '2-digit', month: 'short', year: 'numeric' });
}

// ---- Status badge HTML ----
function statusBadge(status) {
    const map = {
        PENDING:   '<span class="badge badge-pending">Pending</span>',
        APPROVED:  '<span class="badge badge-approved">Approved</span>',
        CANCELLED: '<span class="badge badge-cancelled">Cancelled</span>',
    };
    return map[status] || status;
}

// ---- Resource type badge ----
function typeBadge(type) {
    const map = {
        VENUE:     '<span class="badge badge-venue">Venue</span>',
        EQUIPMENT: '<span class="badge badge-equipment">Equipment</span>',
    };
    return map[type] || type;
}

// ---- Set minimum date to today ----
function setMinDate(inputId) {
    const el = document.getElementById(inputId);
    if (el) el.min = new Date().toISOString().split('T')[0];
}

// ---- Confirm modal helper ----
function confirmDelete(callback) {
    if (window.confirm('Are you sure you want to delete this booking? This cannot be undone.')) {
        callback();
    }
}

// ---- Load resources into a <select> ----
async function loadResourceSelect(selectId, filterType = null) {
    const select = document.getElementById(selectId);
    if (!select) return;
    select.innerHTML = '<option value="">Loading resources...</option>';
    try {
        let url = filterType ? `/resources/${filterType.toLowerCase()}` : '/resources';
        const resources = await apiFetch(url);
        select.innerHTML = '<option value="">— Select a resource —</option>';
        const venues = resources.filter(r => r.type === 'VENUE');
        const equipment = resources.filter(r => r.type === 'EQUIPMENT');

        if (venues.length > 0) {
            const vg = document.createElement('optgroup');
            vg.label = '📍 Venues';
            venues.forEach(r => {
                const o = document.createElement('option');
                o.value = r.id;
                o.textContent = r.name;
                if (!r.available) { o.textContent += ' (Unavailable)'; o.disabled = true; }
                vg.appendChild(o);
            });
            select.appendChild(vg);
        }
        if (equipment.length > 0) {
            const eg = document.createElement('optgroup');
            eg.label = '🔧 Equipment';
            equipment.forEach(r => {
                const o = document.createElement('option');
                o.value = r.id;
                o.textContent = r.name;
                if (!r.available) { o.textContent += ' (Unavailable)'; o.disabled = true; }
                eg.appendChild(o);
            });
            select.appendChild(eg);
        }
    } catch {
        select.innerHTML = '<option value="">Failed to load resources</option>';
    }
}

// ---- Check availability live ----
async function checkAvailability(resourceId, date, resultEl) {
    if (!resourceId || !date || !resultEl) return;
    resultEl.innerHTML = '<span style="color:var(--gray-500)">Checking availability...</span>';
    try {
        const data = await apiFetch(`/events/check-availability?resourceId=${resourceId}&date=${date}`);
        if (data.available) {
            resultEl.innerHTML = '<span class="availability-dot avail-free"></span><span style="color:var(--success);font-weight:600">Available on this date ✓</span>';
        } else {
            resultEl.innerHTML = '<span class="availability-dot avail-taken"></span><span style="color:var(--danger);font-weight:600">Already booked on this date ✗</span>';
        }
    } catch {
        resultEl.innerHTML = '';
    }
}

// ---- Active nav link ----
document.addEventListener('DOMContentLoaded', () => {
    const links = document.querySelectorAll('.nav-links a');
    const current = window.location.pathname.split('/').pop() || 'index.html';
    links.forEach(link => {
        if (link.getAttribute('href') === current) link.classList.add('active');
    });
});
