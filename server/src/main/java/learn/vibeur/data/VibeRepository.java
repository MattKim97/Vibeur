package learn.vibeur.data;

import learn.vibeur.models.Vibe;

import java.util.List;

public interface VibeRepository {

    public Vibe create(Vibe vibe);

    public Vibe findById(int vibeId);

    public List<Vibe> findAll();

    public List<Vibe> findByUserId(int userId);

    public boolean update(Vibe vibe);

    public boolean deleteById(int vibeId);

}
