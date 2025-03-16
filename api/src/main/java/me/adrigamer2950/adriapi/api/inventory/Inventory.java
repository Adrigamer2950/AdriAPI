package me.adrigamer2950.adriapi.api.inventory;

import lombok.Getter;
import lombok.NonNull;
import me.adrigamer2950.adriapi.api.user.User;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Create inventories
 *
 * @since 2.1.0
 */
@SuppressWarnings("unused")
@Getter
public abstract class Inventory implements InventoryHolder {

    private final org.bukkit.inventory.Inventory inventory;
    private final User user;

    public Inventory(@NotNull User user) {
        this(user, null);
    }

    public Inventory(@NotNull User user, @Nullable Component title) {
        this(user, title, InventorySize.THREE_ROWS);
    }

    public Inventory(@NotNull User user, @Nullable Component title, @NotNull InventorySize size) {
        this(user, title, size.getSize());
    }

    public Inventory(@NotNull @NonNull User user, @Nullable Component title, int size) {
        if (!user.isPlayer())
            throw new IllegalArgumentException("User must be a player");

        this.user = user;
        this.inventory = title == null
                ? Bukkit.createInventory(this, size)
                : Bukkit.createInventory(this, size, title);
    }

    /**
     * Setup inventory items ({@link #setupInventory()})
     * and open the inventory to the player
     */
    public void openInventory() {
        this.setupInventory();

        this.user.getPlayerOrNull().openInventory(this.getInventory());
    }

    /**
     * Setup items in the inventory or any other thing you may want to do
     */
    protected void setupInventory() { }

    /**
     * Executed when a player clicks in the inventory
     *
     * @param e An {@link InventoryClickEvent}.
     *          Null check on {@link InventoryClickEvent#getClickedInventory()}
     *          is unnecessary as it is checked before executing this method
     */
    public void onInventoryClick(@NotNull InventoryClickEvent e) { }

    /**
     * @param e An {@link InventoryCloseEvent}
     */
    public void onInventoryClose(@NotNull InventoryCloseEvent e) { }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private User user;
        private Component title;
        private int size = InventorySize.THREE_ROWS.getSize();
        private Consumer<@NotNull Inventory> setupInventory;
        private BiConsumer<@NotNull InventoryClickEvent, @NotNull Inventory> onInventoryClick;
        private BiConsumer<@NotNull InventoryCloseEvent, @NotNull Inventory> onInventoryClose;

        public Builder user(@NonNull User user) {
            this.user = user;
            return this;
        }

        public Builder title(Component title) {
            this.title = title;
            return this;
        }

        public Builder size(InventorySize size) {
            return this.size(size.getSize());
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder setupInventory(Consumer<@NotNull Inventory> setupInventory) {
            this.setupInventory = setupInventory;
            return this;
        }

        public Builder onInventoryClick(Consumer<@NotNull InventoryClickEvent> onInventoryClick) {
            return onInventoryClick((e, i) -> onInventoryClick.accept(e));
        }

        public Builder onInventoryClick(BiConsumer<@NotNull InventoryClickEvent, @NotNull Inventory> onInventoryClick) {
            this.onInventoryClick = onInventoryClick;
            return this;
        }

        public Builder onInventoryClose(Consumer<@NotNull InventoryCloseEvent> onInventoryClose) {
            return onInventoryClose((e, i) -> onInventoryClose.accept(e));
        }

        public Builder onInventoryClose(BiConsumer<@NotNull InventoryCloseEvent, @NotNull Inventory> onInventoryClose) {
            this.onInventoryClose = onInventoryClose;
            return this;
        }

        public Inventory build() {
            if (this.user == null)
                throw new IllegalArgumentException("User cannot be null");

            return new Inventory(this.user, this.title, this.size) {
                @Override
                protected void setupInventory() {
                    if (setupInventory != null)
                        setupInventory.accept(this);
                }

                @Override
                public void onInventoryClick(@NotNull InventoryClickEvent e) {
                    if (onInventoryClick != null)
                        onInventoryClick.accept(e, this);
                }

                @Override
                public void onInventoryClose(@NotNull InventoryCloseEvent e) {
                    if (onInventoryClose != null)
                        onInventoryClose.accept(e, this);
                }
            };
        }
    }
}
