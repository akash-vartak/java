package de.benjaminborbe.poker.util;

import de.benjaminborbe.poker.api.PokerCardIdentifier;
import de.benjaminborbe.poker.api.PokerCardValue;
import de.benjaminborbe.tools.map.MapList;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

public class PokerCardsTwoPairComparator implements Comparator<Collection<PokerCardIdentifier>> {

	private final PokerCardsHighcardComparator pokerCardsHighcardComparator;

	@Inject
	public PokerCardsTwoPairComparator(final PokerCardsHighcardComparator pokerCardsHighcardComparator) {
		this.pokerCardsHighcardComparator = pokerCardsHighcardComparator;
	}

	@Override
	public int compare(final Collection<PokerCardIdentifier> cardsA, final Collection<PokerCardIdentifier> cardsB) {
		final List<PokerCardIdentifier> listA = buildList(cardsA);
		final List<PokerCardIdentifier> listB = buildList(cardsB);
		if (listA.size() < 2 && listB.size() < 2) {
			return 0;
		}
		final int result = pokerCardsHighcardComparator.compare(listA, listB);
		if (result != 0) {
			return result;
		}
		return pokerCardsHighcardComparator.compare(buildRemaining(cardsA, listA), buildRemaining(cardsB, listB));
	}

	private Collection<PokerCardIdentifier> buildRemaining(final Collection<PokerCardIdentifier> cards, final List<PokerCardIdentifier> list) {
		final List<PokerCardIdentifier> result = new ArrayList<PokerCardIdentifier>();
		for (final PokerCardIdentifier card : cards) {
			if (!list.contains(card)) {
				result.add(card);
			}
		}
		return result;
	}

	private List<PokerCardIdentifier> buildList(final Collection<PokerCardIdentifier> cards) {
		final List<PokerCardIdentifier> list = new ArrayList<PokerCardIdentifier>();
		final MapList<PokerCardValue, PokerCardIdentifier> map = buildMap(cards);
		for (final Entry<PokerCardValue, List<PokerCardIdentifier>> e : map.entrySet()) {
			if (e.getValue().size() == 2) {
				list.add(e.getValue().get(0));
			}
		}
		return list;
	}

	private MapList<PokerCardValue, PokerCardIdentifier> buildMap(final Collection<PokerCardIdentifier> cards) {
		final MapList<PokerCardValue, PokerCardIdentifier> result = new MapList<PokerCardValue, PokerCardIdentifier>();
		for (final PokerCardIdentifier card : cards) {
			result.add(card.getValue(), card);
		}
		return result;
	}
}
