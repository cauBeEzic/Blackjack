package com.caubeezic.blackjack.web;

import com.caubeezic.blackjack.domain.GameOutcome;
import com.caubeezic.blackjack.domain.GameState;
import com.caubeezic.blackjack.web.SessionStats;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlackjackController {
    private static final String SESSION_KEY = "game";
    private static final String SESSION_GAMES = "gamesByTabId";
    private static final String SESSION_STATS = "statsByTabId";

    @GetMapping("/")
    public String index(@RequestParam(required = false) String tabId, HttpSession session, Model model) {
        if (tabId == null || tabId.isBlank()) {
            String newId = UUID.randomUUID().toString();
            return "redirect:/?tabId=" + urlEncode(newId);
        }
        GameState game = getOrCreateGame(session, tabId);
        SessionStats stats = getOrCreateStats(session, tabId);
        applyRoundResultsIfNeeded(game, stats);
        model.addAttribute("game", game);
        model.addAttribute("stats", stats);
        model.addAttribute("tabId", tabId);
        return "index";
    }

    @PostMapping("/new")
    public String newGame(@RequestParam String tabId, HttpSession session) {
        getGamesByTab(session).put(tabId, new GameState());
        return "redirect:/?tabId=" + urlEncode(tabId);
    }

    @PostMapping("/hit")
    public String hit(@RequestParam String tabId, HttpSession session) {
        GameState game = getOrCreateGame(session, tabId);
        game.playerHit();
        SessionStats stats = getOrCreateStats(session, tabId);
        applyRoundResultsIfNeeded(game, stats);
        return "redirect:/?tabId=" + urlEncode(tabId);
    }

    @PostMapping("/stand")
    public String stand(@RequestParam String tabId, HttpSession session) {
        GameState game = getOrCreateGame(session, tabId);
        game.playerStand();
        SessionStats stats = getOrCreateStats(session, tabId);
        applyRoundResultsIfNeeded(game, stats);
        return "redirect:/?tabId=" + urlEncode(tabId);
    }

    @PostMapping("/split")
    public String split(@RequestParam String tabId, HttpSession session) {
        GameState game = getOrCreateGame(session, tabId);
        game.playerSplit();
        return "redirect:/?tabId=" + urlEncode(tabId);
    }

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "ok";
    }

    private GameState getOrCreateGame(HttpSession session, String tabId) {
        Map<String, GameState> games = getGamesByTab(session);
        return games.computeIfAbsent(tabId, id -> new GameState());
    }

    private Map<String, GameState> getGamesByTab(HttpSession session) {
        Map<String, GameState> games = (Map<String, GameState>) session.getAttribute(SESSION_GAMES);
        if (games == null) {
            games = new HashMap<>();
            session.setAttribute(SESSION_GAMES, games);
        }
        return games;
    }

    private SessionStats getOrCreateStats(HttpSession session, String tabId) {
        Map<String, SessionStats> statsByTab = (Map<String, SessionStats>) session.getAttribute(SESSION_STATS);
        if (statsByTab == null) {
            statsByTab = new HashMap<>();
            session.setAttribute(SESSION_STATS, statsByTab);
        }
        return statsByTab.computeIfAbsent(tabId, id -> new SessionStats());
    }

    private void applyRoundResultsIfNeeded(GameState game, SessionStats stats) {
        if (!game.isRoundComplete() || game.isResultsApplied()) {
            return;
        }
        for (GameOutcome outcome : game.getHandOutcomes()) {
            if (outcome == null) {
                continue;
            }
            switch (outcome) {
                case WIN:
                case BLACKJACK:
                    stats.incrementWins();
                    break;
                case LOSE:
                case BUST:
                    stats.incrementLosses();
                    break;
                case PUSH:
                    stats.incrementPushes();
                    break;
                default:
                    break;
            }
        }
        game.markResultsApplied();
    }

    private String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
