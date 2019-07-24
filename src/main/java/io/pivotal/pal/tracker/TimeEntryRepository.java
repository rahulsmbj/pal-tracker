package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {

    TimeEntry create(TimeEntry entity);
    TimeEntry find(Long id);
    List<TimeEntry> list();
    void delete(Long id);
    TimeEntry update(Long id, TimeEntry entity);
}
